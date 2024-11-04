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

def generar_grupo():
    nombre = fake.word() + " " + fake.word()
    imagen = fake.image_url()
    
    return {
        "nombre": nombre,
        "imagen": imagen
    }

def insertar_grupo(grupo):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO grupo (nombre, imagen)
                 VALUES (%s, %s)"""
        
        cursor.execute(sql, (
            grupo['nombre'], 
            grupo['imagen']
        ))
        
        conn.commit()
        print(f"Grupo '{grupo['nombre']}' insertado correctamente.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

for _ in range(100):
    grupo = generar_grupo()
    insertar_grupo(grupo)
