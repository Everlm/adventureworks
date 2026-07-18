package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.infrastructure.persistence.entity.BusinessEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityJpaRepository extends JpaRepository<BusinessEntityEntity, Integer> {
}
