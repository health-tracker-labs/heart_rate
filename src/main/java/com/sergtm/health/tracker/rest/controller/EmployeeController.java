package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.rest.controller.mapper.EmployeeMapper;
import com.sergtm.health.tracker.rest.response.EmployeeResponse;
import com.sergtm.health.tracker.service.impl.EmployeePatientAssignmentService;
import com.sergtm.health.tracker.service.impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private final EmployeePatientAssignmentService employeePatientAssignmentService;

    @GetMapping
    public Collection<EmployeeResponse> getEmployees() {
        Collection<Employee> employees = employeeService.getEmployees();
        return employeeMapper.toResponses(employees);
    }

    @PutMapping("/{employeeId}/patients/{patientId}")
    public EmployeeResponse assignPatientToEmployee(@PathVariable Long employeeId,
                                                 @PathVariable Long patientId) {
        Employee employee = employeePatientAssignmentService.assignPatientToEmployee(employeeId, patientId);
        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping("/{employeeId}/patients/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse removePatientFromEmployee(@PathVariable Long employeeId,
                                          @PathVariable Long patientId) {
        Employee employee = employeePatientAssignmentService.removePatientFromEmployee(employeeId, patientId);
        return employeeMapper.toResponse(employee);
    }
}
