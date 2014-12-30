package org.tuxdevelop.spring_data_repositories.repository;


import org.springframework.data.repository.CrudRepository;
import org.tuxdevelop.spring_data_repositories.domain.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByFirstName(final String firstName);
    List<Customer> findAll();
    List<Customer> findAll(Iterable<Long> ids);
}
