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

def generar_actividad():
    nombre = fake.catch_phrase()
    descripcion = fake.paragraph()
    imagen = fake.image_url()
    fecha = fake.date()
    ubicacion = fake.address()
    
    return {
        "nombre": nombre,
        "descripcion": descripcion,
        "imagen": imagen,
        "fecha": fecha,
        "ubicacion": ubicacion
    }

def insertar_actividad(actividad):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO actividad (nombre, descripcion, imagen, fecha, ubicacion)
                 VALUES (%s, %s, %s, %s, %s)"""
        
        cursor.execute(sql, (
            actividad['nombre'], 
            actividad['descripcion'], 
            actividad['imagen'], 
            actividad['fecha'], 
            actividad['ubicacion']
        ))
        
        conn.commit()
        print(f"Actividad '{actividad['nombre']}' insertada correctamente.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

for _ in range(100):
    actividad = generar_actividad()
    insertar_actividad(actividad)
