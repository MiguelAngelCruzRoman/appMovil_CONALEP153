from faker import Faker
import random
import mysql.connector
import os
from datetime import datetime, timedelta

db_config = {
    'host': 'localhost',
    'user': os.getenv('DB_USER', 'root'),
    'password': os.getenv('DB_PASSWORD', ''),
    'database': 'DB_CONALEP153_APIs'
}

fake = Faker('es_mx')

def obtener_docentes():
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = "SELECT id_usuario FROM usuario WHERE rol = 'DOCENTE'"
        cursor.execute(sql)
        docentes = cursor.fetchall()
        return [docente[0] for docente in docentes]
    except mysql.connector.Error as err:
        print(f"Error al obtener docentes: {err}")
        return []
    finally:
        cursor.close()
        conn.close()

def generar_horas_clase():
    inicio = random.randint(8, 17)
    minuto_inicio = random.choice([0, 15, 30, 45])
    hora_inicio = datetime.strptime(f"{inicio}:{minuto_inicio:02d}", "%H:%M")
    fin = hora_inicio + timedelta(hours=1)
    hora_inicio_str = hora_inicio.strftime("%I:%M %p")
    hora_fin_str = fin.strftime("%I:%M %p")
    return f"{hora_inicio_str} - {hora_fin_str}"

def generar_modulo(docentes):
    id_docente = random.choice(docentes)
    tipo_formacion = random.choice(['DISCIPLINAR BASICA', 'PROFESIONAL', 'TRAYECTO TECNICO'])
    horas_clase = generar_horas_clase()
    nombre_modulo = fake.bs().title()
    salon_clase = fake.word().capitalize() + " " + str(random.randint(1, 20))
    diaSemana = random.choice(['LUN','MAR','MIE','JUE','VIE'])
    return {
        "id_docente": id_docente,
        "tipoFormacion": tipo_formacion,
        "horasClase": horas_clase,
        "nombreModulo": nombre_modulo,
        "salonClase": salon_clase,
        "diaSemana": diaSemana,
    }

def insertar_modulo(modulo):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = """INSERT INTO modulo (id_docente, tipoFormacion, horasClase, nombreModulo, salonClase, diaSemana)
                 VALUES (%s, %s, %s, %s, %s, %s)"""
        cursor.execute(sql, (
            modulo['id_docente'],
            modulo['tipoFormacion'],
            modulo['horasClase'],
            modulo['nombreModulo'],
            modulo['salonClase'],
            modulo['diaSemana']
        ))
        conn.commit()
        print(f"Módulo '{modulo['nombreModulo']}' insertado correctamente para docente ID {modulo['id_docente']}.")
    except mysql.connector.Error as err:
        print(f"Error al insertar módulo: {err}")
    finally:
        cursor.close()
        conn.close()

def main():
    docentes = obtener_docentes()
    if not docentes:
        print("No se encontraron docentes en la base de datos.")
        return
    for _ in range(100):
        modulo = generar_modulo(docentes)
        insertar_modulo(modulo)

if __name__ == "__main__":
    main()
