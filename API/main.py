import requests
import matplotlib.pyplot as plt
from matplotlib.dates import DateFormatter
import datetime


url = "http://127.0.0.1:8000/"

def plot_measurements(data):
    sensor_data = {}

    for measurement in data['Measurements']:
        date_str = str(measurement['Date'])
        date = datetime.datetime.strptime(date_str, '%Y-%m-%dT%H:%M:%S')

        origin = measurement['Origin']
        concentration = measurement['Concentration']

        if origin not in sensor_data:
            sensor_data[origin] = {'dates': [], 'concentrations': []}

        sensor_data[origin]['dates'].append(date)
        sensor_data[origin]['concentrations'].append(concentration)

    fig, ax = plt.subplots()

    for origin, values in sensor_data.items():
        ax.scatter(values['dates'], values['concentrations'], label=origin)

    # Ajustar leyendas
    ax.legend(loc='upper left', bbox_to_anchor=(1, 1))

    # Formato de fecha en el eje x
    date_format = DateFormatter('%Y-%m-%d %H:%M:%S')
    ax.xaxis.set_major_formatter(date_format)
    fig.autofmt_xdate()

    plt.xlabel('Date and Time')
    plt.ylabel('Concentration')
    plt.title('Concentration Over Time for Each Sensor')
    plt.show()


while True:
        print("\nSeleccione una opción:")
        print("1. Ver mediciones")
        print("2. Obtener mediciones por fecha")
        print("3. Obtener máximos históricos")
        print("x. Salir")

        option = input("Opción: ")

        if option == "1":
                response = requests.get(url + "getAll")
                for measurement in response.json()['Measurements']:
                        print(measurement) 

        elif option == "2":
                fecha_inicio = input("Fecha Inicial: (YYYY-MM-DD HH:MM:SS): ")
                fecha_fin = input("Fecha Final: (YYYY-MM-DD HH:MM:SS): ")
                response = requests.get(url + "getByDate" , json={"init_date": fecha_inicio, "end_date": fecha_fin})
                for measurement in response.json()['Measurements']:
                        print(measurement) 
                plot = input("¿Desea graficar los datos? (y/n): ")
                if plot == "y":
                        plot_measurements(response.json())

                

        elif option == "3":
                response = requests.get(url + "getMax")
                for measurement in response.json()['Measurements']:
                        print(measurement) 

        elif option == "x":
                break

