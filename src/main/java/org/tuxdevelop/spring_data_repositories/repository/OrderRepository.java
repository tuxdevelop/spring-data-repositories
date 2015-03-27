package org.tuxdevelop.spring_data_repositories.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.Order;
import org.tuxdevelop.spring_data_repositories.domain.CustomerOverview;

public interface OrderRepository extends ReadOnlyRepository<Order, Long>, OrderRepositoryExtension {

    Iterable<Order> findOrdersByCustomer(@Param("customer")final Customer customer);
}
