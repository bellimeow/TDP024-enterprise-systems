use crate::models::{Bank, BankName, BankKey};
use crate::Pool;
use crate::kafkalogger;

use actix_web::http::StatusCode;
use actix_web::{web, Error, HttpResponse};
use anyhow::Result;
use diesel::RunQueryDsl;


// default route
pub async fn root() -> Result<HttpResponse, Error> {
    Ok(HttpResponse::build(StatusCode::OK)
        .body("REST API in Rust!"))
}

// endpoint for /bank/list
pub async fn get_banks(pool: web::Data<Pool>) -> Result<HttpResponse, Error> {
    //kafkalogger::send("GET request for bank/list".to_string());
    Ok(list_banks(pool)
    .await
    .map(|some_bank| HttpResponse::Ok().json(some_bank))
    .map_err(|_| HttpResponse::InternalServerError())?)
}

// endpoint for find bank by name
pub async fn get_bank_name(pool: web::Data<Pool>, query: web::Query<BankName>) -> Result<HttpResponse, Error> {

    let requested_name = &query.name;
    
    //kafkalogger::send("GET request for bank/find.name: ".to_string() + &requested_name.to_string());

    if let Ok(record) = get_name(pool, requested_name.to_string()) {
        Ok(HttpResponse::Ok().json(record))
    } else {
        Ok(HttpResponse::Ok().body("null"))
    }
}

// endpoint for find bank by key
pub async fn get_bank_key(pool: web::Data<Pool>, query: web::Query<BankKey>) -> Result<HttpResponse, Error> {

    let requested_key = &query.key;

    //kafkalogger::send("GET request for bank/find.key".to_string() + &requested_key.to_string());
    
    if let Ok(record) = get_key(pool, requested_key.to_string()) {
        Ok(HttpResponse::Ok().json(record))
    } else {
        Ok(HttpResponse::Ok().body("null"))     
    }
}

// load from database
async fn list_banks(pool: web::Data<Pool>) -> Result<Vec<Bank>, diesel::result::Error> {
    use crate::schema::banks::dsl::*;
    let db_connection = pool.get().unwrap();
    let result = banks.load(&db_connection)?;
    Ok(result)
}

fn get_name(pool: web::Data<Pool>, req_name: String) -> Result<Option<Bank>, diesel::result::Error> {
    use crate::schema::banks::dsl::*;
    let db_connection = pool.get().unwrap();
    let result = banks.load::<Bank>(&db_connection)?;

    println!("{:?}", result);
    
    for record in result {
        println!("{:?}", record);
        if record.name == req_name {
            return Ok(Some(record))
        }
    }
    return Ok(None)
}

fn get_key(pool: web::Data<Pool>, req_key: String) -> Result<Option<Bank>, diesel::result::Error> {
    use crate::schema::banks::dsl::*;
    let db_connection = pool.get().unwrap();
    let result = banks.load::<Bank>(&db_connection)?;

    println!("{:?}", result);
    
    for record in result {
        println!("{:?}", record);
        if record.key.to_string() == req_key {
            return Ok(Some(record))
        }
    }
    return Ok(None);
}