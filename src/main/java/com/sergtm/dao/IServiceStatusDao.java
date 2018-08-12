package com.sergtm.dao;

import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;

import java.util.Collection;

public interface IServiceStatusDao {
    Collection<ServiceStatus> getAll();
    ServiceStatus getByName(ServiceName serviceName);
    void update(ServiceStatus serviceStatus);
}
