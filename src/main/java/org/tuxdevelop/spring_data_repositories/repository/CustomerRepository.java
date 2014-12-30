package org.tuxdevelop.spring_data_repositories.repository;


import org.springframework.data.repository.Repository;
import org.tuxdevelop.spring_data_repositories.domain.Customer;

public interface CustomerRepository extends Repository<Customer, Long>{

    Customer findOne(final Long id);

    Iterable<Customer> findByFirstName(final String firstName);

}
