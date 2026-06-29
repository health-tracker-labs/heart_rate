package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.rest.response.EmployeeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = BaseMapperConfig.class,
        uses = {PatientMapper.class, PersonMapper.class})
public interface EmployeeMapper {
    List<EmployeeResponse> toResponses(Iterable<Employee> employees);
    EmployeeResponse toResponse(Employee employee);
}
