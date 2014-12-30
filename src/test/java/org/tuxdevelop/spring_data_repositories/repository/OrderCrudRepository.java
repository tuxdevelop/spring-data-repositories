package org.tuxdevelop.spring_data_repositories.repository;

import org.springframework.data.repository.CrudRepository;
import org.tuxdevelop.spring_data_repositories.domain.Order;

public interface OrderCrudRepository extends CrudRepository<Order,Long>{
}
