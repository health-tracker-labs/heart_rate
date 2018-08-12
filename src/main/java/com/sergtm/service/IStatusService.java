package com.sergtm.service;

import com.sergtm.model.ServiceName;

public interface IStatusService {
    String identifyLastModifiedService();
    void updateAndSave(ServiceName serviceName);
}
