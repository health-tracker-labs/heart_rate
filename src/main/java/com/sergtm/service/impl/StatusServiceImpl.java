package com.sergtm.service.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.Response;
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

    @Autowired
    private IServiceStatusDao serviceStatusDao;

    @Override
    public Response identifyLastModifiedService() {
        Collection<ServiceStatus> serviceStatuses = serviceStatusDao.getAll();

        if (serviceStatuses.isEmpty()) {
            return new Response(ServiceName.None, Duration.of(0, ChronoUnit.MINUTES));
        }

        List<ServiceStatus> serviceStatusesSorted = serviceStatuses
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if (serviceStatusesSorted.get(0).getLastModificationTime() == null) {
            return new Response(serviceStatusesSorted.get(0).getServiceName(), Duration.of(0, ChronoUnit.MINUTES));
        }

        Duration durationBetweenLastMod =
                Duration.between(serviceStatusesSorted.get(0).getLastModificationTime(), LocalDateTime.now());
        Duration durationBetweenCalls =
                Duration.of(10, ChronoUnit.MINUTES);

        Duration deference = durationBetweenLastMod.minus(durationBetweenCalls);
        if (deference.isNegative()) {
            return new Response(ServiceName.None, deference);
        }
        return new Response(serviceStatusesSorted.get(serviceStatuses.size() - 1).getServiceName(),
                Duration.of(0, ChronoUnit.MINUTES));
    }

    @Override
    public void updateAndSave(ServiceName serviceName) {
        ServiceStatus serviceStatus = serviceStatusDao.getByName(serviceName);
        serviceStatus.setLastModificationTime(LocalDateTime.now());
        serviceStatusDao.update(serviceStatus);
    }
}