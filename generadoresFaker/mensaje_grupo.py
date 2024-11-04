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

def generar_mensaje_grupo(num_usuarios, num_grupos):
    id_grupo = random.randint(1, num_grupos)
    id_usuario = random.randint(1, num_usuarios)
    fecha = fake.date()
    contenido = fake.sentence()
    
    return {
        "id_grupo": id_grupo,
        "id_usuario": id_usuario,
        "fecha": fecha,
        "contenido": contenido
    }

def insertar_mensaje_grupo(mensaje_grupo):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO mensaje_grupo (id_grupo, id_usuario, fecha, contenido)
                 VALUES (%s, %s, %s, %s)"""
        
        cursor.execute(sql, (
            mensaje_grupo['id_grupo'], 
            mensaje_grupo['id_usuario'], 
            mensaje_grupo['fecha'], 
            mensaje_grupo['contenido']
        ))
        
        conn.commit()
        print(f"Mensaje en grupo insertado correctamente para usuario ID {mensaje_grupo['id_usuario']} en grupo ID {mensaje_grupo['id_grupo']}.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

num_usuarios = 100  # Número total de usuarios
num_grupos = 100  # Número total de grupos

for _ in range(100):
    mensaje_grupo = generar_mensaje_grupo(num_usuarios, num_grupos)
    insertar_mensaje_grupo(mensaje_grupo)
