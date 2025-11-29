This is a Kafka implementation
How to start Kafka without Zookeeper

B. Switch to KRaft mode and format

Edit C:\kafka_2.13-3.9.1\config\server.properties (or create a KRaft config file) and add the KRaft keys:
node.id=1
process.roles=broker,controller
listeners=PLAINTEXT://:9092,CONTROLLER://:9093
controller.listener.names=CONTROLLER
controller.quorum.voters=1@localhost:9093
metadata.log.dir=C:/kafka_2.13-3.9.1/kraft-combined-logs
log.dirs=C:/kafka_2.13-3.9.1/kafka-logs

//Then generate id + format:
cd C:\kafka_2.13-3.9.1\bin\windows
kafka-storage.bat random-uuid
kafka-storage.bat format -t <THE-ID-YOU-GOT> -c ..\..\config\server.properties
kafka-server-start.bat ..\..\config\server.properties

API for Testing
1) GET 
localhost:8080/api/v1/kafka/publish?message=welcome kafa 

2) POST 
localhost:8080/api/v1/kafka/publish
{
    "id":111,
    "firstName":"Sham",
    "lastName":"Mahajana"
}

2) POST
localhost:8080/api/v1/kafka/single/publis
{
    "id":12,
    "firstName":"Sham",
    "lastName":"Mahajana"
}
