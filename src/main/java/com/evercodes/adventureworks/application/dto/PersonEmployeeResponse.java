package com.evercodes.adventureworks.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEmployeeResponse 
{
    private String firstName;
    private String lastName;
    private String jobTitle;
    private LocalDate hireDate;
}
