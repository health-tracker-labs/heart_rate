package com.sergtm.health.tracker.testsupport.response;

import com.sergtm.health.tracker.rest.response.PatientResponse;

public final class PatientResponseFixture {
    private PatientResponseFixture() {
    }

    public static PatientResponse.PatientResponseBuilder createPatientResponseBuilder() {
        return PatientResponse.builder();
    }
}
