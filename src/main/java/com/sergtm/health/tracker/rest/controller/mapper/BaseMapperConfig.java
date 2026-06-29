package com.sergtm.health.tracker.rest.controller.mapper;

import org.mapstruct.MapperConfig;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.ERROR;

@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = ERROR,
        injectionStrategy = CONSTRUCTOR
)
public interface BaseMapperConfig {
}
