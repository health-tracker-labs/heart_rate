CREATE OR REPLACE VIEW heart_rate_with_atm_pressure AS
    SELECT
        concat(p.id,coalesce(h.id,0) ) AS id,
        coalesce(h.upper_pressure,0) AS upperpressure,
        coalesce(h.lower_pressure,0) AS lowerpressure,
        coalesce(h.bpm,0) AS beatsperminute,
        coalesce(p.pressure, 0) AS weatherpressure,
        COALESCE (p.CONCRETE_DATE, h.CONCREATE_DATE) as concrete_date,
        h.person_id AS person_id
    FROM
        heart_rate h
        FULL JOIN pressure p ON trunc(h.concreate_date) = trunc(p.concrete_date);