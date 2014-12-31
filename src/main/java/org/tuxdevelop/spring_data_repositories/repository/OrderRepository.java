package org.tuxdevelop.spring_data_repositories.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.Order;

@RepositoryRestController
public interface OrderRepository extends ReadOnlyRepository<Order, Long>, OrderRepositoryExtension {

    Iterable<Order> findOrdersByCustomer(@Param("customer")final Customer customer);
}
