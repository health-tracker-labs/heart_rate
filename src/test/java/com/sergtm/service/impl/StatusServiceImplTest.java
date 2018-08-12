package com.sergtm.service.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import com.sergtm.service.IStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StatusServiceImplTest {
    public static final String NONE = "None";

    @Mock
    private IServiceStatusDao serviceStatusDao;
    @InjectMocks
    private IStatusService statusService = new StatusServiceImpl();

    @Test
    public void shouldReturnNoneIfGetAllReturnEmptyCollection(){
        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList());
        assertEquals(NONE, statusService.identifyLastModifiedService());
    }

    @Test
    public void shouldReturnPressureService(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(11));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, LocalDateTime.now().minusMinutes(20));

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(weatherServiceStatus, pressureServiceStatus));

        assertEquals(ServiceName.PressureService.name(), statusService.identifyLastModifiedService());
    }

    @Test
    public void shouldReturnNoneIfTenMinutesHaveNotPassed(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(5));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, LocalDateTime.now().minusMinutes(20));

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(weatherServiceStatus, pressureServiceStatus));

        assertEquals(NONE, statusService.identifyLastModifiedService());
    }

    @Test
    public void shouldReturnPressureServiceIfPressureServiceDateFieldIsNull(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, LocalDateTime.now().minusMinutes(11));

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, null);

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(pressureServiceStatus, weatherServiceStatus));

        assertEquals(ServiceName.PressureService.name(), statusService.identifyLastModifiedService());
    }

    @Test
    public void shouldReturnPressureServiceIfBothDateFieldsAreNull(){
        ServiceStatus weatherServiceStatus =
                createServiceStatusEntity(ServiceName.WeatherService, null);

        ServiceStatus pressureServiceStatus =
                createServiceStatusEntity(ServiceName.PressureService, null);

        when(serviceStatusDao.getAll()).thenReturn(Arrays.asList(pressureServiceStatus, weatherServiceStatus));

        assertEquals(ServiceName.PressureService.name(), statusService.identifyLastModifiedService());
    }

    private ServiceStatus createServiceStatusEntity(ServiceName serviceName, LocalDateTime localDateTime){
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setServiceName(serviceName);
        serviceStatus.setLastModificationTime(localDateTime);
        return serviceStatus;
    }
}