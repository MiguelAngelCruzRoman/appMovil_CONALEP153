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

def obtener_usuarios():
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = "SELECT id_usuario FROM usuario"
        cursor.execute(sql)
        usuarios = cursor.fetchall()
        return [usuario[0] for usuario in usuarios]
    except mysql.connector.Error as err:
        print(f"Error al obtener módulos: {err}")
        return []
    finally:
        cursor.close()
        conn.close()

def obtener_grupos():
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = "SELECT id_grupo FROM grupo"
        cursor.execute(sql)
        grupos = cursor.fetchall()
        return [grupo[0] for grupo in grupos]
    except mysql.connector.Error as err:
        print(f"Error al obtener grupos: {err}")
        return []
    finally:
        cursor.close()
        conn.close()

def generar_grupo_usuario(usuarios, grupos):
    id_usuario = random.choice(usuarios)
    id_grupo = random.choice(grupos)
    return {
        "id_usuario": id_usuario,
        "id_grupo": id_grupo
    }

def insertar_grupo_usuario(grupo_usuario):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = """INSERT INTO grupo_usuario (id_usuario, id_grupo)
                 VALUES (%s, %s)"""
        cursor.execute(sql, (
            grupo_usuario['id_usuario'],
            grupo_usuario['id_grupo']
        ))
        conn.commit()
        print(f"Módulo-grupo insertado correctamente: Módulo ID {grupo_usuario['id_usuario']} - Grupo ID {grupo_usuario['id_grupo']}.")
    except mysql.connector.Error as err:
        print(f"Error al insertar módulo-grupo: {err}")
    finally:
        cursor.close()
        conn.close()

def main():
    usuarios = obtener_usuarios()
    grupos = obtener_grupos()

    if not usuarios or not grupos:
        print("No se encontraron módulos o grupos en la base de datos.")
        return

    for _ in range(100):
        grupo_usuario = generar_grupo_usuario(usuarios, grupos)
        insertar_grupo_usuario(grupo_usuario)

if __name__ == "__main__":
    main()
