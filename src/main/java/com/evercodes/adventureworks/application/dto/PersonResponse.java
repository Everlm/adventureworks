package com.evercodes.adventureworks.application.dto;

import com.evercodes.adventureworks.domain.model.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse 
{

    private Integer businessEntityId;
    private PersonType personType;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
}
