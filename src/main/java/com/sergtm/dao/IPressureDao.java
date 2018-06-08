package com.sergtm.dao;

import com.sergtm.entities.Pressure;
import oracle.jdbc.proxy.annotation.Pre;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface IPressureDao {
    void deletePressure(Pressure pressure);
    Pressure getByDate(LocalDate date);
    void addPressure(Pressure pressure);
    Collection<Pressure> getAll();
}
