package com.sergtm.dao;

import com.sergtm.entities.HeartRateWithWeatherPressure;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IHeartRateWithWeatherDao {
	Collection<HeartRateWithWeatherPressure> getData(LocalDateTime from, LocalDateTime to, Long personId);
}
