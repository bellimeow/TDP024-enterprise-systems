#[macro_use]
extern crate diesel;

mod models;
mod routes;
mod schema;
mod kafkalogger;

use actix_web::{web, App, HttpServer};
use diesel::r2d2::{self, ConnectionManager};
use diesel::SqliteConnection;

pub type Pool = r2d2::Pool<ConnectionManager<SqliteConnection>>;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();

    let database_url = std::env::var("DATABASE_URL").expect("NOT FOUND");
    let database_pool = Pool::builder()
        .build(ConnectionManager::new(database_url))
        .unwrap();

    HttpServer::new(move || {
        App::new()
            .data(database_pool.clone())
            .route("/", web::get().to(routes::root))
            .route("/bank/list", web::get().to(routes::get_banks))
            .route("/bank/find.name", web::get().to(routes::get_bank_name))
            .route("/bank/find.key", web::get().to(routes::get_bank_key))

    })
    .bind("localhost:8070")?
    .run()
    .await
}