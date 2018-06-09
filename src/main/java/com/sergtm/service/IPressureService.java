package com.sergtm.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface IPressureService {
    void addAll(Map<LocalDate, Double> weatherMap);
}
