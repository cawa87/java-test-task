create table account (
  id int serial primary key,
  login varchar(255) not null,
  password varchar(255) not null,
  account_details varchar(1024),
  personal_info varchar(1024)
);

create unique index ix_account_by_login on account(login);

insert into account (login, password, personal_info) values ('admin', '$2a$10$X3cvIv67jC.P0kRjm6gcHeNn0eVwTA70MxlooHq1qLrdd5My2lD4u', 'Admin the Firstborn.');