package com.sergtm.model;

import java.time.Duration;

public class Response {
    private ServiceName serviceName;
    private Duration timeBeforeAvailability;

    public Response() {
    }

    public Response(ServiceName serviceName, Duration timeBeforeAvailability) {
        this.serviceName = serviceName;
        this.timeBeforeAvailability = timeBeforeAvailability;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public Duration getTimeBeforeAvailability() {
        return timeBeforeAvailability;
    }

    public void setTimeBeforeAvailability(Duration timeBeforeAvailability) {
        this.timeBeforeAvailability = timeBeforeAvailability;
    }
}
