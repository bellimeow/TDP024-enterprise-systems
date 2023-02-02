use kafka::error::Error as KafkaError;
use kafka::producer::{Producer, Record};

pub fn send(message: String) {

    let broker = "localhost:9092";
    let topic = "rest-requests";
    let id = "BankService";

    let data = message.as_bytes();
    println!("Producing request to log");

    if let Err(e) = produce_message(data, topic, id, vec![broker.to_owned()]) {
        println!("Failed producing messages: {}", e);
    }
}

pub fn produce_message<'a, 'b, 'c>(
    data: &'a [u8],
    topic: &'b str,
    id: &'c str,
    brokers: Vec<String>,
) -> Result<(), KafkaError> {

    let mut producer = Producer::from_hosts(brokers)
        //.with_ack_timeout(Duration::from_secs(1))
        //.with_required_acks(RequiredAcks::One)
        .create()?;

    producer.send(&Record::from_key_value(topic, id, data))?; 

    Ok(())
}