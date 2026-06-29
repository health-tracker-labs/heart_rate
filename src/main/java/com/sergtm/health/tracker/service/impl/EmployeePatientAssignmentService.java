package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.PatientIsNotAssignedToEmployeeException;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeePatientAssignmentService {
    private static final String PATIENT_IS_NOT_ASSIGNED_TO_EMPLOYEE_MSG =
            "Patient with id [%s] is not assigned to employee with id [%s]";

    private final EmployeeService employeeService;
    private final PatientService patientService;

    @Transactional
    public Employee assignPatientToEmployee(Long employeeId, Long patientId) {
        Employee employee = employeeService.getEmployeeByIdOrThrowException(employeeId);
        Patient patient = patientService.getPatientByIdOrThrowException(patientId);

        employee.getPatients().add(patient);

        return employee;
    }

    @Transactional
    public Employee removePatientFromEmployee(Long employeeId, Long patientId) {
        Employee employee = employeeService.getEmployeeByIdOrThrowException(employeeId);
        Patient toBeRemoved = patientService.getPatientByIdOrThrowException(patientId);

        boolean isRemove = employee.getPatients().removeIf(patient -> patient.equals(toBeRemoved));
        if (!isRemove) {
            throw new PatientIsNotAssignedToEmployeeException(
                    String.format(PATIENT_IS_NOT_ASSIGNED_TO_EMPLOYEE_MSG, patientId, employeeId));
        }
        return employee;
    }
}
