package com.sergtm.health.tracker.testsupport.entry;

import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;

import java.util.Set;

public final class EmployeeEntryFixture {
    private EmployeeEntryFixture() {
    }

    public static Employee createEmployee(Person person, Set<Patient> patients) {
        return createEmployeeBuilder()
                .person(person)
                .patients(patients)
                .build();
    }

    public static Employee.EmployeeBuilder createEmployeeBuilder() {
        return Employee.builder();
    }
}
