from faker import Faker
import random
import mysql.connector
import os

db_config = {
    'host': 'localhost',     
    'user': os.getenv('DB_USER', 'root'),  
    'password': os.getenv('DB_PASSWORD', ''), 
    'database': 'DB_CONALEP153_APIs'
}

fake = Faker('es_mx')

def generar_mensaje_docente(num_docentes, num_alumnos):
    id_docente = random.randint(1, num_docentes)
    id_alumno = random.randint(1, num_alumnos)
    fecha = fake.date()
    contenido = fake.sentence()
    
    return {
        "id_docente": id_docente,
        "id_alumno": id_alumno,
        "fecha": fecha,
        "contenido": contenido
    }

def insertar_mensaje_docente(mensaje_docente):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO mensaje_docente (id_docente, id_alumno, fecha, contenido)
                 VALUES (%s, %s, %s, %s)"""
        
        cursor.execute(sql, (
            mensaje_docente['id_docente'], 
            mensaje_docente['id_alumno'], 
            mensaje_docente['fecha'], 
            mensaje_docente['contenido']
        ))
        
        conn.commit()
        print(f"Mensaje del docente insertado correctamente para docente ID {mensaje_docente['id_docente']} y alumno ID {mensaje_docente['id_alumno']}.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

num_usuarios = 100  # Número total de usuarios

for _ in range(100):
    mensaje_docente = generar_mensaje_docente(num_usuarios, num_usuarios)
    insertar_mensaje_docente(mensaje_docente)
