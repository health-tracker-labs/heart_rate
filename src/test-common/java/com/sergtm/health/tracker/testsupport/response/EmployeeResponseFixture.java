package com.sergtm.health.tracker.testsupport.response;

import com.sergtm.health.tracker.rest.response.EmployeeResponse;

public final class EmployeeResponseFixture {
    private EmployeeResponseFixture() {
    }

    public static EmployeeResponse.EmployeeResponseBuilder createEmployeeResponseBuilder() {
        return EmployeeResponse.builder();
    }
}
