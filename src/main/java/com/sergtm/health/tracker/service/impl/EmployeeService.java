package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.EmployeeNotFoundException;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService {
    private static final String CAN_NOT_FIND_EMPLOYEE_BY_EMPLOYEE_ID_MSG = "Can't find employee by employee id = %s";

    private final EmployeeRepository employeeRepository;

    public Collection<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByIdOrThrowException(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format(CAN_NOT_FIND_EMPLOYEE_BY_EMPLOYEE_ID_MSG, employeeId)));
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        requireNonNull(employee);
        return employeeRepository.save(employee);
    }
}
