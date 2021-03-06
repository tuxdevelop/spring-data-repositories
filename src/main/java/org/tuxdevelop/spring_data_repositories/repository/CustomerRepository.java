package org.tuxdevelop.spring_data_repositories.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.CustomerOverview;

import java.util.List;

@RepositoryRestResource(excerptProjection = CustomerOverview.class)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>,
        QueryDslPredicateExecutor<Customer> {

    List<Customer> findByFirstName(@Param("firstName")final String firstName);

    List<Customer> findAll();

    @Transactional(timeout = 10)
    List<Customer> findAll(Iterable<Long> ids);

    List<Customer> findCustomersByContactCity(@Param("city")final String city);

    @Query("SELECT c FROM Customer c where c.contact.city = :city")
    List<Customer> findCustomersByCity(@Param("city") final String city);

    @Query(nativeQuery = true, value = "SELECT * From Customer c JOIN Contact co ON c.contact_id = co.id WHERE co.city = :city")
    List<Customer> findCustomerByCityNative(@Param("city") final String city);
}
