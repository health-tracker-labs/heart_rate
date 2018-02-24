DROP TABLE HEART_RATE.HEART_RATE;
DROP TABLE HEART_RATE.PERSON;
DROP TABLE HEART_RATE.HELP;
DROP TABLE HEART_RATE.TOPIC;
DROP TABLE HEART_RATE.USER;


CREATE TABLE HEART_RATE.PERSON(
ID bigint NOT NULL PRIMARY KEY auto_increment,
FIRST_NAME VARCHAR(20) NOT NULL,
MIDDLE_NAME VARCHAR(20),
SECOND_NAME VARCHAR(20),
COUNTRY VARCHAR (30),
CITY VARCHAR (30),
BIRTHDATE DATE,
PHONE VARCHAR(30),
MOBILE_PHONE VARCHAR(30),
EMAIL VARCHAR(30)
);

create table heart_rate.USER(
ID bigint not null primary key auto_increment,
USERNAME varchar(20) not null,
PASSWORD varchar(20) not null,
ROLE varchar(20) not null
);

insert into HEART_RATE.USER(username, password, role) values('admin', 'admin', 'ADMIN');

CREATE TABLE HEART_RATE.HEART_RATE(
ID bigint NOT NULL PRIMARY KEY auto_increment,
UPPER_PRESSURE integer NOT NULL,
LOWER_PRESSURE integer NOT NULL,
BPM integer NOT NULL,
CONCREATE_DATE DATE NOT NULL,
PERSON_ID bigint NOT NULL
);

ALTER TABLE HEART_RATE.HEART_RATE ADD CONSTRAINT fk_heart_rate_person_id FOREIGN KEY(PERSON_ID) REFERENCES heart_rate.PERSON(ID) ON DELETE CASCADE;


CREATE TABLE HEART_RATE.HELP(
ID bigint NOT NULL PRIMARY KEY auto_increment,
NAME VARCHAR(30) NOT NULL,
DESCRIPTION longtext NOT NULL,
TOPIC_ID bigint NOT NULL
);


CREATE TABLE HEART_RATE.TOPIC(
ID bigint NOT NULL PRIMARY KEY auto_increment,
NAME VARCHAR(30) NOT NULL
);

ALTER TABLE HEART_RATE.HELP ADD CONSTRAINT fk_help_topic_id FOREIGN KEY(TOPIC_ID) REFERENCES heart_rate.TOPIC(ID) ON DELETE CASCADE;

insert into heart_rate.TOPIC(NAME) values('PERSON');
insert into heart_rate.TOPIC(NAME) values('HEART_RATE');

insert into heart_rate.help (NAME, description, TOPIC_ID) values ('Add Heart Rate', 'adds heart rate ', 2);

insert into heart_rate.help (NAME, description, TOPIC_ID) values ('Add Heart Rate By Id', 'adds heart rate. Insted of name and second name persons id can be inserted', 2);