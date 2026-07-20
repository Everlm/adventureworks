package com.evercodes.adventureworks.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Employee", schema = "HumanResources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity 
{

    @Id
    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    @Column(name = "NationalIDNumber", length = 15)
    private String nationalIdNumber;

    @Column(name = "LoginID", length = 256)
    private String loginId;

    @Column(name = "JobTitle", length = 50)
    private String jobTitle;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "MaritalStatus", columnDefinition = "nchar(1)")
    private String maritalStatus;

    @Column(name = "Gender", columnDefinition = "nchar(1)")
    private String gender;

    @Column(name = "HireDate")
    private LocalDate hireDate;

    @Column(name = "SalariedFlag")
    private boolean salariedFlag;

    @Column(name = "VacationHours")
    private short vacationHours;

    @Column(name = "SickLeaveHours")
    private short sickLeaveHours;

    @Column(name = "CurrentFlag")
    private boolean currentFlag;

    @OneToOne
    @JoinColumn(name = "BusinessEntityID")
    private PersonEntity person;
}
