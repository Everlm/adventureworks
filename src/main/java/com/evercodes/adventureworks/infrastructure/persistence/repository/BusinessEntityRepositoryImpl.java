package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.infrastructure.persistence.entity.BusinessEntityEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessEntityRepositoryImpl implements BusinessEntityRepository {

    private final BusinessEntityJpaRepository jpaRepository;

    public BusinessEntityRepositoryImpl(BusinessEntityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BusinessEntity save(BusinessEntity businessEntity) {
        BusinessEntityEntity entity = new BusinessEntityEntity();
        BusinessEntityEntity saved = jpaRepository.save(entity);
        return new BusinessEntity(saved.getBusinessEntityId());
    }
}
