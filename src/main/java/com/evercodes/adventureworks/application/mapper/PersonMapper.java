package com.evercodes.adventureworks.application.mapper;

import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PersonMapper 
{
    @Mapping(target = "businessEntityId", ignore = true)
    @Mapping(target = "nameStyle", ignore = true)
    @Mapping(target = "emailPromotion", ignore = true)
    Person toDomain(PersonRequest request);

    @Mapping(target = "businessEntityId", ignore = true)
    @Mapping(target = "nameStyle", ignore = true)
    @Mapping(target = "emailPromotion", ignore = true)
    void updateDomainFromRequest(PersonRequest request, @MappingTarget Person person);

    PersonResponse toResponse(Person person);
    
    List<PersonResponse> toResponseList(List<Person> persons);
}
