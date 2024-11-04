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

def generar_mensaje_actividad(num_usuarios, num_actividades):
    id_actividad = random.randint(1, num_actividades)
    id_usuario = random.randint(1, num_usuarios)
    fecha = fake.date()
    contenido = fake.sentence()
    
    return {
        "id_actividad": id_actividad,
        "id_usuario": id_usuario,
        "fecha": fecha,
        "contenido": contenido
    }

def insertar_mensaje_actividad(mensaje_actividad):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO mensaje_actividad (id_actividad, id_usuario, fecha, contenido)
                 VALUES (%s, %s, %s, %s)"""
        
        cursor.execute(sql, (
            mensaje_actividad['id_actividad'], 
            mensaje_actividad['id_usuario'], 
            mensaje_actividad['fecha'], 
            mensaje_actividad['contenido']
        ))
        
        conn.commit()
        print(f"Mensaje de actividad insertado correctamente para usuario ID {mensaje_actividad['id_usuario']} en actividad ID {mensaje_actividad['id_actividad']}.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

num_usuarios = 100  # Número total de usuarios
num_actividades = 100  # Número total de actividades

for _ in range(100):
    mensaje_actividad = generar_mensaje_actividad(num_usuarios, num_actividades)
    insertar_mensaje_actividad(mensaje_actividad)
