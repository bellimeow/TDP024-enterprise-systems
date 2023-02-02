### To run this project make sure you have:
    - Zookeeper server running
    - Kafka server running
    - PersonService running
    - BankService running

#### If you do not have kafka install you can download it from here:
https://kafka.apache.org/downloads

#### Start the ZooKeeper service
###### Linux
$ bin/zookeeper-server-start.sh config/zookeeper.properties

###### Windows
$ bin\windows\zookeeper-server-start.bat config/zookeeper.properties


#### Open another terminal session
#### Start the Kafka broker service

###### Linux
$ bin/kafka-server-start.sh config/server.properties

###### Windows
$ bin\windows\kafka-server-start.bat config/server.properties

##### create topics
###### Linux
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic rest-requests
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic transactions

###### Windows
$ bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic rest-requests
$ bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic transactions

##### Start consumer

###### Linux
$ bin/kafka-console-consumer.sh --topic rest-requests --from-beginning --bootstrap-server localhost:9092
$ bin/kafka-console-consumer.sh --topic transactions --from-beginning --bootstrap-server localhost:9092

###### Windows
$ bin\windows\kafka-console-consumer.bat --topic rest-requests --from-beginning --bootstrap-server localhost:9092
$ bin\windows\kafka-console-consumer.bat --topic transactions --from-beginning --bootstrap-server localhost:9092


#### PersonService
To run the person service you need to have python installed as well as flask and kafka-python

#### BankService