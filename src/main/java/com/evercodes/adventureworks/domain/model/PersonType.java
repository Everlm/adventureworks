package com.evercodes.adventureworks.domain.model;

public enum PersonType {
    GC("General Contact"),
    SP("Sales Person"),
    EM("Employee"),
    IN("Individual Customer"),
    VC("Vendor Contact"),
    SC("Store Contact");

    private final String description;

    PersonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
