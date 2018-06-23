create table heart_rate.pressure(
ID INT NOT NULL PRIMARY KEY,
CONCRETE_DATE date not null,
PRESSURE binary_double not null
);

CREATE SEQUENCE PRESSURE_SEQ;


create view heart_rate.HEART_RATE_WITH_ATM_PRESSURE as select concat(p.id, COALESCE(h.id, 0)) as id, COALESCE(h.upper_pressure, 0)  as upperPressure, COALESCE(h.lower_pressure, 0) as lowerPressure
                , COALESCE(h.bpm, 0) as beatsPerMinute, p.pressure as weatherPressure, p.concrete_date, h.person_id as person_id
                from heart_rate h right join pressure p
                 on h.concreate_date = p.concrete_date;