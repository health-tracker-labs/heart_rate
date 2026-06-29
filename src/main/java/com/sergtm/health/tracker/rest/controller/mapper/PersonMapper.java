package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.request.PersonRequest;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Mapper(config = BaseMapperConfig.class)
public interface PersonMapper {
    String FULL_NAME_FORMAT = "%s%s, %s";

    List<PersonResponse> toResponses(Iterable<Person> persons);

    @Mapping(target = "name", source = ".", qualifiedByName = "toFullName")
    PersonResponse toResponse(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "mobilePhone", ignore = true)
    Person toDomain(PersonRequest personRequest);

    @Named("toFullName")
    default String getFullName(Person person) {
        String middleName = formatMiddleName(person.getMiddleName());
        return String.format(FULL_NAME_FORMAT, person.getSecondName(),
                middleName, person.getFirstName());
    }

    private String formatMiddleName(String middleName) {
        return isEmpty(middleName) ? EMPTY : SPACE + middleName;
    }
}
