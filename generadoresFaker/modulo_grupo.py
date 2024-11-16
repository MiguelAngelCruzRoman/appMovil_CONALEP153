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

def obtener_modulos():
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = "SELECT id_modulo FROM modulo"
        cursor.execute(sql)
        modulos = cursor.fetchall()
        return [modulo[0] for modulo in modulos]
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

def generar_modulo_grupo(modulos, grupos):
    id_modulo = random.choice(modulos)
    id_grupo = random.choice(grupos)
    return {
        "id_modulo": id_modulo,
        "id_grupo": id_grupo
    }

def insertar_modulo_grupo(modulo_grupo):
    try:
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()
        sql = """INSERT INTO modulo_grupo (id_modulo, id_grupo)
                 VALUES (%s, %s)"""
        cursor.execute(sql, (
            modulo_grupo['id_modulo'],
            modulo_grupo['id_grupo']
        ))
        conn.commit()
        print(f"Módulo-grupo insertado correctamente: Módulo ID {modulo_grupo['id_modulo']} - Grupo ID {modulo_grupo['id_grupo']}.")
    except mysql.connector.Error as err:
        print(f"Error al insertar módulo-grupo: {err}")
    finally:
        cursor.close()
        conn.close()

def main():
    modulos = obtener_modulos()
    grupos = obtener_grupos()

    if not modulos or not grupos:
        print("No se encontraron módulos o grupos en la base de datos.")
        return

    for _ in range(100):
        modulo_grupo = generar_modulo_grupo(modulos, grupos)
        insertar_modulo_grupo(modulo_grupo)

if __name__ == "__main__":
    main()
