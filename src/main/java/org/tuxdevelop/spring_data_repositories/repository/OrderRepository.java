package org.tuxdevelop.spring_data_repositories.repository;

import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.Order;

public interface OrderRepository extends ReadOnlyRepository<Order, Long>, OrderRepositoryExtension {

    Iterable<Order> findOrdersByCustomer(final Customer customer);
}
