import pika, sys, os
from models import Measurement
import json
import requests

def main():
    url = "http://127.0.0.1:8000/insert"
    
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='rules')

    def callback(ch, method, properties, body):
        json_body = json.loads(body)
        if json_body['Concentration'] > 0.3:
            print("Fuga de gas detectada por" + json_body['Origin'])
        response = requests.post(url, json=json_body)
        print(response)


    channel.basic_consume(queue='rules', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()




if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)