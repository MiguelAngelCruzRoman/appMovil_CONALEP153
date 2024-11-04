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

def generar_actividad_usuario(num_usuarios, num_actividades):
    id_usuario = random.randint(1, num_usuarios)
    id_actividad = random.randint(1, num_actividades)
    
    return {
        "id_usuario": id_usuario,
        "id_actividad": id_actividad
    }

def insertar_actividad_usuario(actividad_usuario):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO actividad_usuario (id_usuario, id_actividad)
                 VALUES (%s, %s)"""
        
        cursor.execute(sql, (
            actividad_usuario['id_usuario'], 
            actividad_usuario['id_actividad']
        ))
        
        conn.commit()
        print(f"Actividad de usuario insertada correctamente para usuario ID {actividad_usuario['id_usuario']} en actividad ID {actividad_usuario['id_actividad']}.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

num_usuarios = 100  # Número total de usuarios
num_actividades = 100  # Número total de actividades

for _ in range(100):
    actividad_usuario = generar_actividad_usuario(num_usuarios, num_actividades)
    insertar_actividad_usuario(actividad_usuario)
