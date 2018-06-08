drop table user_role;
drop table users;
drop table role;
drop SEQUENCE USERS_SEQ;
drop SEQUENCE ROLE_SEQ;
drop SEQUENCE USER_ROLE_SEQ;



create table USERS(
ID INT not null primary key,
USERNAME varchar2(20) not null,
PASSWORD varchar2(20) not null
);

CREATE SEQUENCE USERS_SEQ;


create table heart_rate.ROLE(
ID int not null primary key,
NAME varchar(20) not null
);

create sequence ROLE_SEQ;

create table heart_rate.USER_ROLE(
ID int not null primary key,
USER_ID int not null,
ROLE_ID int not null
);

ALTER TABLE USER_ROLE ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY(USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE ENABLE;
ALTER TABLE USER_ROLE ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ID) ON DELETE CASCADE ENABLE;

create sequence USER_ROLE_SEQ;