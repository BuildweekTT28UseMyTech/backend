package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.RentedItem;
import org.springframework.data.repository.CrudRepository;

public interface RentedItemRepository extends CrudRepository<RentedItem, Long> {
    void deleteByRenteditemid(long id);
}
