package org.tuxdevelop.spring_data_repositories.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tuxdevelop.spring_data_repositories.configuration.PersistenceConfiguration;
import org.tuxdevelop.spring_data_repositories.domain.Customer;

import javax.transaction.Transactional;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
@Transactional
public class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findOneIT(){
        final Customer customer = customerRepository.findOne(1L);
        Assert.assertNotNull(customer);
        Assert.assertEquals("Donnie",customer.getFirstName());
        Assert.assertEquals("Darko",customer.getLastName());
    }

    @Test
    public void findByFirstNameIT(){
        final Iterable<Customer> customers = customerRepository.findByFirstName("Frank");
        Assert.assertNotNull(customers);
        final Iterator<Customer> iterator = customers.iterator();
        final Customer customer = iterator.next();
        Assert.assertNotNull(customer);
        Assert.assertEquals("Frank",customer.getFirstName());
        Assert.assertFalse(iterator.hasNext());
    }
}
