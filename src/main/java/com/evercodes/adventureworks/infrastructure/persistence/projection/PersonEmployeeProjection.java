package com.evercodes.adventureworks.infrastructure.persistence.projection;

import java.time.LocalDate;

public interface PersonEmployeeProjection {

    String getFirstName();

    String getLastName();

    String getJobTitle();

    LocalDate getHireDate();
}
