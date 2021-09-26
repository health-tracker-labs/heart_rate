drop sequence DISEASE_SEQ;
drop sequence OCCASION_SEQ;

drop table occasion_level;
drop table DISEASE;
drop table OCCASION;

create sequence DISEASE_SEQ;
CREATE TABLE DISEASE 
(
  "ID" NUMBER NOT NULL, 
  "DESCRIPTION" VARCHAR2(300 BYTE), 
  "NAME" VARCHAR2(20 BYTE), 
  "WITH_CONVULSION" CHAR(1) DEFAULT 'N' NOT NULL,
  CONSTRAINT DISEASE_PK PRIMARY KEY (ID)
);

create sequence OCCASION_SEQ;
CREATE TABLE OCCASION 
(
  "ID" NUMBER NOT NULL, 
  "DISEASE_ID" NUMBER NOT NULL, 
  "OCCASION_DATE" DATE NOT NULL,
  "OCCASION_LEVEL" VARCHAR2(20) NOT NULL,
  CONSTRAINT OCCASION_PK PRIMARY KEY ("ID")
);

ALTER TABLE OCCASION ADD CONSTRAINT OCCASION_DISEASE_ID_FK FOREIGN KEY ("DISEASE_ID") REFERENCES DISEASE ("ID") ENABLE;

insert into DISEASE (ID,DESCRIPTION,NAME) values ('2','Хроническое неврологическое заболевание, проявляющееся в предрасположенности организма к внезапному возникновению судорожных приступов','epilepsy');