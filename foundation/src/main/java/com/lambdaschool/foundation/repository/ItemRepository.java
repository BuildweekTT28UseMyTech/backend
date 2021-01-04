package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends CrudRepository<Item, Long> {

    void deleteByItemid(long id);
}
