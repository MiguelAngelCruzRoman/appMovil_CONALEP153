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

def obtener_usuarios_por_rol(rol):
    """Obtiene los IDs de los usuarios filtrados por rol."""
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = "SELECT id_usuario FROM usuario WHERE rol = %s"
        cursor.execute(sql, (rol,))
        
        usuarios = cursor.fetchall()
        return [usuario[0] for usuario in usuarios]  
        
    except mysql.connector.Error as err:
        print(f"Error al obtener usuarios por rol: {err}")
        return []
    finally:
        cursor.close()
        conn.close()

def generar_mensaje_docente(docentes, alumnos):
    """Genera un mensaje aleatorio con un docente y un alumno."""
    id_docente = random.choice(docentes)  
    id_alumno = random.choice(alumnos)    
    fecha = fake.date()
    contenido = fake.sentence()
    
    return {
        "id_docente": id_docente,
        "id_alumno": id_alumno,
        "fecha": fecha,
        "contenido": contenido
    }

def insertar_mensaje_docente(mensaje_docente):
    """Inserta un mensaje en la base de datos."""
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

def main():
    docentes = obtener_usuarios_por_rol('DOCENTE')
    alumnos = obtener_usuarios_por_rol('ALUMNO')
    
    if not docentes or not alumnos:
        print("No se encontraron docentes o alumnos en la base de datos.")
        return
    
    for _ in range(100):
        mensaje_docente = generar_mensaje_docente(docentes, alumnos)
        insertar_mensaje_docente(mensaje_docente)

if __name__ == "__main__":
    main()
