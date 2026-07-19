package com.evercodes.adventureworks.infrastructure.persistence.mapper;

import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.infrastructure.persistence.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonEntityMapper {

    PersonEntityMapper INSTANCE = Mappers.getMapper(PersonEntityMapper.class);

    @Mapping(source = "businessEntityId", target = "businessEntityId")
    @Mapping(source = "personType", target = "personType")
    @Mapping(source = "nameStyle", target = "nameStyle")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "suffix", target = "suffix")
    @Mapping(source = "emailPromotion", target = "emailPromotion")
    Person toDomain(PersonEntity entity);

    @Mapping(source = "businessEntityId", target = "businessEntityId")
    @Mapping(source = "personType", target = "personType")
    @Mapping(source = "nameStyle", target = "nameStyle")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "suffix", target = "suffix")
    @Mapping(source = "emailPromotion", target = "emailPromotion")
    PersonEntity toEntity(Person person);
}
