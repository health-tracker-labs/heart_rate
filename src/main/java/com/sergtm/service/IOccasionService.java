package com.sergtm.service;

import java.time.LocalDateTime;

import com.sergtm.OccasionLevel;

public interface IOccasionService {

	void addOccasion(OccasionLevel occasionLevel, boolean convulsion, LocalDateTime ldt);

}
