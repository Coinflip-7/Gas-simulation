import mysql.connector


# Función para conectar a la base de datos
async def db_open_connection():
    try:
        # Configuración de la conexión
        config = {
            'user': 'gas_user',
            'password': 'gaspass',
            'host': 'localhost',
            'database': 'Gas',
        }
        conexion = mysql.connector.connect(**config)

        if conexion.is_connected():
            print('Conexión establecida con éxito.')

        cursor = conexion.cursor()

        return conexion, cursor

    except mysql.connector.Error as err:
        print(f'Error: {err}')
        return None, None

async def db_close_connection(conexion, cursor):
    try:
        if cursor is not None:
            cursor.close()

        if conexion is not None and conexion.is_connected():
            conexion.close()
            print('Conexión cerrada.')

    except Exception as e:
        print(f'Error al cerrar la conexión: {e}')

async def insert_measurement(conexion, cursor, fecha, origen, concentracion):
    try:
        consulta = "INSERT INTO Measurements (Date, Origin, Concentration) VALUES (%s, %s, %s)"
        datos = (fecha,origen, concentracion)
        cursor.execute(consulta, datos)
        conexion.commit()

        print("Inserción exitosa.")
        return True

    except mysql.connector.Error as err:
        print(f'Error al insertar la medición: {err}')
        return False

async def get_all_measurement(conexion, cursor):
    try:
        consulta = "SELECT * FROM Measurements"
        cursor.execute(consulta)
        columnas = [col[0] for col in cursor.description]
        resultados = [dict(zip(columnas, fila)) for fila in cursor.fetchall()]
        return resultados

    except mysql.connector.Error as err:
        print(f'Error al obtener todos los datos: {err}')
        return None

async def get_measurement_by_date(conexion, cursor, fecha_inicio, fecha_fin):
    try:
        consulta = "SELECT Date, Origin, Concentration FROM Measurements WHERE Date BETWEEN %s AND %s"
        datos = (fecha_inicio, fecha_fin)

        cursor.execute(consulta, datos)
        columnas = [col[0] for col in cursor.description]
        resultados = [dict(zip(columnas, fila)) for fila in cursor.fetchall()]
        return resultados

    except mysql.connector.Error as err:
        print(f'Error al obtener todos los datos: {err}')
        return None
    
async def get_max_measurement(conexion, cursor):
    try:
        consulta = "SELECT * FROM Measurements WHERE Concentration = (SELECT MAX(Concentration) FROM Measurements)"
        cursor.execute(consulta)
        columnas = [col[0] for col in cursor.description]
        resultados = [dict(zip(columnas, fila)) for fila in cursor.fetchall()]
        return resultados

    except mysql.connector.Error as err:
        print(f'Error al obtener el máximo histórico: {err}')
        return None

