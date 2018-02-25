DROP TABLE HEART_RATE.HEART_RATE;
DROP TABLE HEART_RATE.PERSON;
DROP TABLE HEART_RATE.HELP;
DROP TABLE HEART_RATE.TOPIC;
DROP TABLE HEART_RATE.USER;
DROP SEQUENCE HEART_RATE.PERSON_SEQ;
DROP SEQUENCE HEART_RATE.HEART_RATE_SEQ;
DROP SEQUENCE HEART_RATE.HELP_SEQ;
DROP SEQUENCE HEART_RATE.TOPIC_SEQ;
DROP SEQUENCE HEART_RATE.USER_SEQ;

CREATE TABLE HEART_RATE.PERSON(
ID INT NOT NULL PRIMARY KEY,
FIRST_NAME VARCHAR2(20) NOT NULL,
MIDDLE_NAME VARCHAR2(20),
SECOND_NAME VARCHAR2(20),
COUNTRY VARCHAR2 (30),
CITY VARCHAR2 (30),
BIRTHDATE DATE,
PHONE VARCHAR2(30),
MOBILE_PHONE VARCHAR2(30),
EMAIL VARCHAR2(30)
);

CREATE SEQUENCE HEART_RATE.PERSON_SEQ;

create table heart_rate.USER(
ID INT not null primary key,
USERNAME varchar2(20) not null,
PASSWORD varchar2(20) not null,
ROLE varchar2(20) not null
);

CREATE SEQUENCE HEART_RATE.USER_SEQ;

insert into HEART_RATE.USER_(id, username, password, role) values(1,'admin', 'admin', 'ADMIN');

CREATE TABLE HEART_RATE.HEART_RATE(
ID INT NOT NULL PRIMARY KEY,
UPPER_PRESSURE INT NOT NULL,
LOWER_PRESSURE INT NOT NULL,
CONCREATE_DATE DATE NOT NULL,
PERSON_ID INT NOT NULL
);

ALTER TABLE HEART_RATE.HEART_RATE ADD CONSTRAINT fk_heart_rate_person_id FOREIGN KEY(PERSON_ID) REFERENCES heart_rate.PERSON(ID) ON DELETE CASCADE ENABLE;
alter table heart_rate.heart_rate add BPM int default 0 not null

CREATE SEQUENCE HEART_RATE.HEART_RATE_SEQ;

CREATE TABLE HEART_RATE.HELP(
ID INT NOT NULL PRIMARY KEY,
NAME VARCHAR2(30) NOT NULL,
DESCRIPTION CLOB NOT NULL,
TOPIC_ID INT NOT NULL
);

CREATE SEQUENCE HEART_RATE.HELP_SEQ;

CREATE TABLE HEART_RATE.TOPIC(
ID INT NOT NULL PRIMARY KEY,
NAME VARCHAR2(30) NOT NULL
);

CREATE SEQUENCE HEART_RATE.TOPIC_SEQ;
ALTER TABLE HEART_RATE.HELP ADD CONSTRAINT fk_help_topic_id FOREIGN KEY(TOPIC_ID) REFERENCES heart_rate.TOPIC(ID) ON DELETE CASCADE ENABLE;

insert into heart_rate.TOPIC(id, NAME) values(HEART_RATE.TOPIC_SEQ.nextval, 'PERSON');
insert into heart_rate.TOPIC(id, NAME) values(HEART_RATE.TOPIC_SEQ.nextval, 'HEART_RATE');

insert into heart_rate.help (id, NAME, description, TOPIC_ID) values (HEART_RATE.HELP_SEQ.nextval, 'Add Heart Rate', '��� ������� ��������� �������� �������� � ���� ������ '
|| '� ���������� ����������� ������ �� �����.'
|| ' ���������: upperPressure - ���� ������� �������t, lowerPressure - ���� ������ ��������, date - ������������ �������� ������� ��������� � ���� ������� �������� ����, 
�� ��������� ��������� ����� �������� ����������,'
|| 'firstName - ��� ������ person, secondName - ������� ������ person', 2);

insert into heart_rate.help (id, NAME, description, TOPIC_ID) values (HEART_RATE.HELP_SEQ.nextval, 'Add Heart Rate By Id', '��� ������� �������� �������� � ���� ������, � ����� ������������ ����� �� ������ ID ����� ������ � PERSON.'
|| ' ���������: id - ���������� �������� ������������� ������ PERSON, upperPressure - ���� ������� �������t, lowerPressure - ���� ������ ��������, date - ������������ �������� ������� ��������� � ���� ������� �������� ����, 
�� ��������� ��������� ����� �������� ����������.', 2);
