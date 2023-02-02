import sqlite3
from sqlite3 import Error

from flask import Flask, request, g, jsonify, redirect
from kafka import KafkaProducer

DBSOURCE = "person.db"
app = Flask(__name__)

BOOTSTRAP_SERVERS = ["localhost:9092"]
TOPIC_NAME = "rest-requests"
CLIENT_ID = "PersonService"

producer = KafkaProducer(
    bootstrap_servers = BOOTSTRAP_SERVERS,
    api_version = (0,11,15),
    client_id = CLIENT_ID
)

def log(message):
    #producer.send(TOPIC_NAME, str.encode(message))
    #producer.flush()
    print(message)

def get_db():
    db = getattr(g,'_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DBSOURCE)
    return db
    
@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

#http://localhost:8060/
@app.route('/')
def this_site():
    return redirect('/person/list')

#http://localhost:8060/person
@app.route('/person', methods=['GET'])
def endpoint():
    cur = get_db().cursor()
    return redirect('/person/list')
    
# lists all persons in the database
# http://localhost:8060/person/list
@app.route('/person/list', methods=['GET'])
def list_all():
    log("GET request to /person/list")
    list = []
    cursor = get_db().cursor()

    try:
        data = cursor.execute("""SELECT * FROM persons;""")
    except Error as e:
        print(e)
        return "ERROR"

    data = data.fetchall()
    for object in data:
        obj = {"key":object[0], "name":object[1]}
        list.append(obj)

    return jsonify(list)



# find person by name
#http://localhost:8060/person/find.name
@app.route('/person/find.name', methods=['GET'])
def find_by_name():
    log("GET request to /person/find.name")
    name = request.args.get('name')
    cursor = get_db().cursor()

    try:
        data = cursor.execute("SELECT * FROM persons WHERE name='{}';".format(name))
    except Error as e:
        print(e)
        return "ERROR"

    row = data.fetchall()
    if row == None:
        return "null"

    if len(row) > 1:
        print(len(row))    
    
    return jsonify(
        key=row[0][0],
        name=row[0][1]
    )


# find person by unique key 
#http://localhost:8060/person/find.key
@app.route('/person/find.key', methods=['GET'])
def find_by_key():
    log("GET request to /person/find.key")
    key = request.args.get('key')
    cursor = get_db().cursor()

    try:
        data = cursor.execute("SELECT * FROM persons WHERE personKey='{}';".format(key))
    except Error as e:
        print(e)
        return "ERROR"

    row = data.fetchone()
    if row == None:
        return "null"
    
    return jsonify(
        key=row[0],
        name=row[1]
    )

if __name__ == '__main__':
    app.run(port=8060)