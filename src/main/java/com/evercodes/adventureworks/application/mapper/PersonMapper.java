package com.evercodes.adventureworks.application.mapper;

import com.evercodes.adventureworks.application.dto.CreatePersonCommand;
import com.evercodes.adventureworks.application.dto.UpdatePersonCommand;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "businessEntityId", ignore = true)
    @Mapping(target = "nameStyle", ignore = true)
    @Mapping(target = "emailPromotion", ignore = true)
    Person toDomain(CreatePersonCommand command);

    @Mapping(target = "businessEntityId", ignore = true)
    @Mapping(target = "nameStyle", ignore = true)
    @Mapping(target = "emailPromotion", ignore = true)
    void updateDomainFromCommand(UpdatePersonCommand command, @MappingTarget Person person);

    PersonResponse toResponse(Person person);
}
