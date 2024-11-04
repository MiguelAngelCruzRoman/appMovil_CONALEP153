from faker import Faker
import random
import mysql.connector
import bcrypt
import os

db_config = {
    'host': 'localhost',     
    'user': os.getenv('DB_USER', 'root'),  
    'password': os.getenv('DB_PASSWORD', ''), 
    'database': 'DB_CONALEP153_APIs'
}

fake = Faker('es_mx')

def generar_usuario():
    primer_nombre = fake.first_name()
    segundo_nombre = fake.first_name() if random.choice([True, False]) else None
    apellido_paterno = fake.last_name()
    apellido_materno = fake.last_name()
    username = f"{primer_nombre.lower()}.{apellido_paterno.lower()}{random.randint(1, 100)}"
    password = fake.password()
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    foto = fake.image_url()
    
    rol = random.choice(["DOCENTE", "ADMINISTRATIVO", "ALUMNO"])
    
    return {
        "primerNombre": primer_nombre,
        "segundoNombre": segundo_nombre,
        "apellidoPaterno": apellido_paterno,
        "apellidoMaterno": apellido_materno,
        "username": username,
        "password": hashed_password,
        "foto": foto,
        "rol": rol
    }

def insertar_usuario(usuario):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        
        sql = """INSERT INTO usuario (primerNombre, segundoNombre, apellidoPaterno, 
                  apellidoMaterno, username, password, foto, rol)
                  VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""
        
        cursor.execute(sql, (
            usuario['primerNombre'], 
            usuario['segundoNombre'], 
            usuario['apellidoPaterno'], 
            usuario['apellidoMaterno'], 
            usuario['username'], 
            usuario['password'], 
            usuario['foto'], 
            usuario['rol']
        ))
        
        conn.commit()
        print(f"Usuario {usuario['username']} insertado correctamente.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        cursor.close()
        conn.close()

for _ in range(100):
    usuario = generar_usuario()
    insertar_usuario(usuario)
