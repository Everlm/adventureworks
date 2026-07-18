package com.evercodes.adventureworks.domain.repository;

import com.evercodes.adventureworks.domain.model.BusinessEntity;

public interface BusinessEntityRepository {

    BusinessEntity save(BusinessEntity businessEntity);
}
