package com.evercodes.adventureworks.presentation.dto;

import com.evercodes.adventureworks.domain.model.PersonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    @NotNull(message = "PersonType is required")
    private PersonType personType;

    private String title;

    @NotBlank(message = "FirstName is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    private String suffix;
}
