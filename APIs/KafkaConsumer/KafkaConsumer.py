from sys import api_version
from kafka import KafkaConsumer

SERVERS = 'localhost:9092'
OFFSET = 'earliest'
API_VERSION = (0,11,15)
CLIENT_ID = 'PythonConsumer'

restConsumer = KafkaConsumer(
    'rest-requests', 
    bootstrap_servers=SERVERS, 
    auto_offset_reset=OFFSET, 
    api_version=API_VERSION
    )

transactionConsumer = KafkaConsumer(
    'transactions',
    bootstrap_servers=SERVERS,
    auto_offset_reset=OFFSET,
    api_version=API_VERSION
)


for message in restConsumer:
    print("rest-requests: {}".format(message.value))

for message in transactionConsumer:
    print("transactions: {}".format(message.value))