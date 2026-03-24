package com.sergtm.service.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import com.sergtm.service.IStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusServiceImplTest {
    @Mock
    private IServiceStatusDao serviceStatusDao;
    @InjectMocks
    private IStatusService statusService = new StatusServiceImpl();

    @Test
    void shouldReturnNoneIfGetAllReturnEmptyCollection(){
        when(serviceStatusDao.getAll()).thenReturn(Collections.EMPTY_LIST);
        assertEquals(ServiceName.None, statusService.identifyLastModifiedService().getServiceName());
    }

    @Test
    void shouldReturnPressureService(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(11));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, LocalDateTime.now().minusMinutes(20));

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(weatherServiceStatus, pressureServiceStatus));

        assertEquals(ServiceName.PressureService, statusService.identifyLastModifiedService().getServiceName());
    }

    @Test
    void shouldReturnNoneIfTenMinutesHaveNotPassed(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(5));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, LocalDateTime.now().minusMinutes(20));

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(weatherServiceStatus, pressureServiceStatus));

        assertEquals(ServiceName.None, statusService.identifyLastModifiedService().getServiceName());
    }

    @Test
    void shouldReturnPressureServiceIfPressureServiceDateFieldIsNull(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(11));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, null);

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(pressureServiceStatus, weatherServiceStatus));

        assertEquals(ServiceName.PressureService, statusService.identifyLastModifiedService().getServiceName());
    }

    @Test
    void shouldReturnPressureServiceIfBothDateFieldsAreNull(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, null);

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, null);

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(pressureServiceStatus, weatherServiceStatus));

        assertEquals(ServiceName.PressureService, statusService.identifyLastModifiedService().getServiceName());
    }

    private static ServiceStatus createServiceStatusEntity(ServiceName serviceName, LocalDateTime localDateTime){
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setServiceName(serviceName);
        serviceStatus.setLastModificationTime(localDateTime);
        return serviceStatus;
    }
}