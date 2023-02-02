from sqlite3 import Error
import sqlite3

try:
    connection = sqlite3.connect("person.db")
    print("database opened successfully")
except Error as e:
    print(e)

drop_if_exist = "DROP TABLE IF EXISTS persons;"

create_table = """CREATE TABLE IF NOT EXISTS persons(
                    personKey INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT
                );"""

populate_table = """
        INSERT INTO persons (name)
        VALUES
            ("Jakob Pogulis"),
            ("Xena"),
            ("Marcus Bendtsen"),
            ("Zorro"),
            ("Q");
        """

connection.execute(drop_if_exist)
connection.execute(create_table)
connection.execute(populate_table)

connection.commit()

result = connection.execute("SELECT * FROM persons")
print(result.fetchall())
connection.close()



