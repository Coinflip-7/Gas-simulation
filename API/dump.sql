-- Crear la base de datos



CREATE DATABASE IF NOT EXISTS Gas;
USE Gas;

-- Crear la tabla 'mediciones'
CREATE TABLE IF NOT EXISTS Measurements (
    Date DATETIME,
    Origin VARCHAR(255),
    Concentration FLOAT
);
