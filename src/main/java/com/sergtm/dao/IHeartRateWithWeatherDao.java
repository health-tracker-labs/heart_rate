package com.sergtm.dao;

import java.util.Collection;
import java.util.Date;

import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.User;

public interface IHeartRateWithWeatherDao {
	Collection<HeartRateWithWeatherPressure> getData(Date from, Date to, Long personId, User user);
}
