package com.sergtm.health.tracker.testsupport.entry;

import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;

public final class PatientEntryFixture {
    private PatientEntryFixture() {
    }

    public static Patient createPatient(Person person) {
        return createPatientBuilder()
                .person(person)
                .build();
    }

    public static Patient.PatientBuilder createPatientBuilder() {
        return Patient.builder();
    }
}
