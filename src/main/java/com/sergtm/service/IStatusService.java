package com.sergtm.service;

import com.sergtm.model.Response;
import com.sergtm.model.ServiceName;

public interface IStatusService {
    Response identifyLastModifiedService();
    void updateAndSave(ServiceName serviceName);
}
