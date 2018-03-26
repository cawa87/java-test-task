create table accounts
(
  id bigserial not null
    constraint accounts_pkey
    primary key,
  account_details varchar(255),
  password varchar(255),
  username varchar(255)
)
;

create table accounts_roles
(
  account_id bigint not null
    constraint fkt44duw96d6v8xrapfo4ff2up6
    references accounts,
  role_id bigint not null
)
;

create table files
(
  id bigserial not null
    constraint files_pkey
    primary key,
  file_name varchar(255),
  file_path varchar(255),
  creator_id bigint
    constraint fkor9qbc3ekckhbdmwx4ab89ied
    references accounts
)
;

create table roles
(
  id bigserial not null
    constraint roles_pkey
    primary key,
  name varchar(255)
    constraint uk_ofx66keruapi6vyqpv6f2or37
    unique
)
;

alter table accounts_roles
  add constraint fkpwest19ib22ux5gk54esw9qve
foreign key (role_id) references roles
;

