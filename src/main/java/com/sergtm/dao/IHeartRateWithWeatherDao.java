package com.sergtm.dao;

import java.time.LocalDateTime;
import java.util.Collection;

import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.User;

public interface IHeartRateWithWeatherDao {
	Collection<HeartRateWithWeatherPressure> getData(LocalDateTime from, LocalDateTime to, Long personId, User user);
}
