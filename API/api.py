from fastapi import FastAPI, Depends, HTTPException
from models import Measurement, MeasurementList, RangeDate
from db_connection import db_open_connection, db_close_connection, insert_measurement, get_all_measurement, get_measurement_by_date, get_max_measurement


app = FastAPI()


@app.post("/insert", status_code=201)
async def endpoint_get_measurement(measurement: Measurement, conexion=Depends(db_open_connection)):
    conexion, cursor = await db_open_connection()
    try:
        resultado = await insert_measurement(conexion, cursor, measurement.Date, measurement.Origin, measurement.Concentration)
        if resultado:
            return {"mensaje": "Inserción exitosa"}
        else:
            raise HTTPException(status_code=500, detail="Error al insertar la medición")

    finally:
        await db_close_connection(conexion,cursor)
        
@app.get("/getAll", response_model=MeasurementList)
async def endpoint_get_all_measurement(conexion=Depends(db_open_connection)):

    conexion, cursor = await db_open_connection()
    try:
        resultados = await get_all_measurement(conexion,cursor)
        if resultados is not None:
            return {"Measurements": resultados}
        else:
            raise HTTPException(status_code=500, detail="Error al obtener todos los datos")

    finally:
        await db_close_connection(conexion,cursor)

@app.get("/getByDate", response_model=MeasurementList)
async def endpoint_get_measeurament_by_date(rangeDate: RangeDate, conexion=Depends(db_open_connection)):

    conexion, cursor = await db_open_connection()
    try:
        resultados = await get_measurement_by_date(conexion,cursor,rangeDate.init_date,rangeDate.end_date)
        if resultados is not None:
            return {"Measurements": resultados}
        else:
            raise HTTPException(status_code=500, detail="Error al obtener todos los datos")

    finally:
        await db_close_connection(conexion,cursor)

@app.get("/getMax", response_model=MeasurementList)
async def endpoint_get_max_measurement(conexion=Depends(db_open_connection)):

    conexion, cursor = await db_open_connection()
    try:
        resultados = await get_max_measurement(conexion,cursor)
        if resultados is not None:
            return {"Measurements": resultados}
        else:
            raise HTTPException(status_code=500, detail="Error al obtener todos los datos")

    finally:
        await db_close_connection(conexion,cursor)

