create table heart_rate.USERS(
ID INT not null primary key,
USERNAME varchar2(20) not null,
PASSWORD varchar2(20) not null,
ROLE varchar2(20) not null
);

CREATE SEQUENCE HEART_RATE.USER_SEQ;

insert into HEART_RATE.USERS(id, username, password, role) values(USER_SEQ.nextval,'admin', 'admin', 'ADMIN');
