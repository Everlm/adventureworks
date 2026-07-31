package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.infrastructure.persistence.entity.EmployeeEntity;
import com.evercodes.adventureworks.infrastructure.persistence.projection.PersonEmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonEmployeeJpaRepository extends JpaRepository<EmployeeEntity, Integer> 
{

    @Query("SELECT p.firstName AS firstName, p.lastName AS lastName, " +
           "e.jobTitle AS jobTitle, e.hireDate AS hireDate " +
           "FROM EmployeeEntity e JOIN e.person p " +
           "WHERE e.nationalIdNumber = :nationalIdNumber")
    Optional<PersonEmployeeProjection> findPersonEmployeeByNationalIdNumber(
        @Param("nationalIdNumber") String nationalIdNumber);
}
