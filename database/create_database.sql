DROP DATABASE IF EXISTS demo;

CREATE DATABASE demo;

USE demo;

CREATE TABLE account (
  account_id int(10)      NOT NULL,
  login varchar(100)      NOT NULL,
  password varchar(100)   NOT NULL,
  full_name varchar(200)  NULL,
  PRIMARY KEY (account_id)
);

INSERT INTO account (account_id, login, password, full_name)
    VALUES (1, 'admin', '123', 'full name');