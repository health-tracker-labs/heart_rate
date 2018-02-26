create table USERS(
ID INT not null primary key,
USERNAME varchar2(20) not null,
PASSWORD varchar2(20) not null,
ROLE varchar2(20) not null
);

CREATE SEQUENCE USERS_SEQ;

insert into USERS(id, username, password, role) values(USERS_SEQ.nextval,'admin', 'admin', 'ADMIN');
