package com.sergtm.dao;

import com.sergtm.entities.Pressure;

import java.time.LocalDate;
import java.util.Collection;

public interface IPressureDao {
    void deletePressure(Pressure pressure);
    Pressure getByDate(LocalDate date);
    void addPressure(Pressure pressure);
    Collection<Pressure> getAll();
}
