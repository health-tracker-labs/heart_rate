package com.sergtm.service.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import com.sergtm.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements IStatusService {
    public static final String NONE = "None";

    @Autowired
    private IServiceStatusDao serviceStatusDao;

    @Override
    public String identifyLastModifiedService() {
        Collection<ServiceStatus> serviceStatuses = serviceStatusDao.getAll();

        if (serviceStatuses.isEmpty()) {
            return NONE;
        }

        List<ServiceStatus> serviceStatusesSorted = serviceStatuses
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if (serviceStatusesSorted.get(0).getLastModificationTime() == null){
            return serviceStatusesSorted.get(0).getServiceName().name();
        }

        Duration duration = Duration.between(serviceStatusesSorted.get(0).getLastModificationTime(), LocalDateTime.now());
        Duration dur = Duration.of(10, ChronoUnit.MINUTES);
        if (duration.minus(dur).isNegative()) {
            return NONE;
        }
        return serviceStatusesSorted.get(serviceStatuses.size() - 1).getServiceName().name();
    }

    @Override
    public void updateAndSave(ServiceName serviceName) {
        ServiceStatus serviceStatus = serviceStatusDao.getByName(serviceName);
        assert serviceName!=null : "Service status is null";

        serviceStatus.setLastModificationTime(LocalDateTime.now());
        serviceStatusDao.update(serviceStatus);
    }
}