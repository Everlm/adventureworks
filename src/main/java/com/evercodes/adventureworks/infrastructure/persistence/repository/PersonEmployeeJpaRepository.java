package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.infrastructure.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonEmployeeJpaRepository extends JpaRepository<EmployeeEntity, Integer> 
{

    @Query("SELECT new com.evercodes.adventureworks.application.dto.PersonEmployeeResponse(" +
           "p.firstName, p.lastName, e.jobTitle, e.hireDate) " +
           "FROM EmployeeEntity e JOIN e.person p " +
           "WHERE e.nationalIdNumber = :nationalIdNumber")
    Optional<PersonEmployeeResponse> findPersonEmployeeByNationalIdNumber(
        @Param("nationalIdNumber") String nationalIdNumber);
}
