//use crate::schema::*;
use std::fmt;
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Queryable, PartialEq)]
pub struct Bank {
    pub key: i32,
    pub name: String,
}

#[derive(Debug, Serialize, Deserialize, Queryable)]
pub struct BankName {
    pub name: String,
}

#[derive(Debug, Serialize, Deserialize, Queryable)]
pub struct BankKey {
    pub key: i32,
}

impl fmt::Display for Bank {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}, {}", self.key, self.name)
    }
}


