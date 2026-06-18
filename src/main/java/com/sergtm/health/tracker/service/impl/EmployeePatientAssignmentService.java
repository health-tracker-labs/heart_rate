package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeePatientAssignmentService {
    private final EmployeeService employeeService;
    private final PatientService patientService;

    @Transactional
    public Employee assignPatientToEmployee(Long employeeId, Long patientId) {
        Employee employee = employeeService.getEmployeeByIdOrThrowException(employeeId);
        Patient patient = patientService.getPatientByIdOrThrowException(patientId);

        employee.getPatients().add(patient);

        return employeeService.saveEmployee(employee);
    }

    @Transactional
    public Employee removePatientFromEmployee(Long employeeId, Long patientId) {
        Employee employee = employeeService.getEmployeeByIdOrThrowException(employeeId);
        Patient toBeRemoved = patientService.getPatientByIdOrThrowException(patientId);

        employee.getPatients().removeIf(patient -> patient.equals(toBeRemoved));

        return employee;
    }
}
