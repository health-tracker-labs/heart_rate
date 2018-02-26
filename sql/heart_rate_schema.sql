DROP TABLE HEART_RATE;
DROP TABLE PERSON;
DROP TABLE HELP;
DROP TABLE TOPIC;
DROP TABLE USER;
DROP SEQUENCE PERSON_SEQ;
DROP SEQUENCE HEART_RATE_SEQ;
DROP SEQUENCE HELP_SEQ;
DROP SEQUENCE TOPIC_SEQ;
DROP SEQUENCE USER_SEQ;

CREATE TABLE PERSON(
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

CREATE SEQUENCE PERSON_SEQ;

CREATE TABLE HEART_RATE(
ID INT NOT NULL PRIMARY KEY,
UPPER_PRESSURE INT NOT NULL,
LOWER_PRESSURE INT NOT NULL,
CONCREATE_DATE DATE NOT NULL,
PERSON_ID INT NOT NULL
);

ALTER TABLE HEART_RATE ADD CONSTRAINT fk_heart_rate_person_id FOREIGN KEY(PERSON_ID) REFERENCES PERSON(ID) ON DELETE CASCADE ENABLE;
alter table heart_rate add BPM int default 0 not null

CREATE SEQUENCE HEART_RATE_SEQ;

CREATE TABLE HELP(
ID INT NOT NULL PRIMARY KEY,
NAME VARCHAR2(30) NOT NULL,
DESCRIPTION CLOB NOT NULL,
TOPIC_ID INT NOT NULL
);

CREATE SEQUENCE HELP_SEQ;

CREATE TABLE TOPIC(
ID INT NOT NULL PRIMARY KEY,
NAME VARCHAR2(30) NOT NULL
);

CREATE SEQUENCE TOPIC_SEQ;
ALTER TABLE HELP ADD CONSTRAINT fk_help_topic_id FOREIGN KEY(TOPIC_ID) REFERENCES TOPIC(ID) ON DELETE CASCADE ENABLE;

insert into TOPIC(id, NAME) values(TOPIC_SEQ.nextval, 'PERSON');
insert into TOPIC(id, NAME) values(TOPIC_SEQ.nextval, 'HEART_RATE');

insert into help (id, NAME, description, TOPIC_ID) values (HELP_SEQ.nextval, 'Add Heart Rate', '��� ������� ��������� �������� �������� � ���� ������ '
|| '� ���������� ����������� ������ �� �����.'
|| ' ���������: upperPressure - ���� ������� �������t, lowerPressure - ���� ������ ��������, date - ������������ �������� ������� ��������� � ���� ������� �������� ����, 
�� ��������� ��������� ����� �������� ����������,'
|| 'firstName - ��� ������ person, secondName - ������� ������ person', 2);

insert into help (id, NAME, description, TOPIC_ID) values (HELP_SEQ.nextval, 'Add Heart Rate By Id', '��� ������� �������� �������� � ���� ������, � ����� ������������ ����� �� ������ ID ����� ������ � PERSON.'
|| ' ���������: id - ���������� �������� ������������� ������ PERSON, upperPressure - ���� ������� �������t, lowerPressure - ���� ������ ��������, date - ������������ �������� ������� ��������� � ���� ������� �������� ����, 
�� ��������� ��������� ����� �������� ����������.', 2);
