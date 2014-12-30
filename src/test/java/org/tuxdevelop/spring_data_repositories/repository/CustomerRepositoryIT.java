package org.tuxdevelop.spring_data_repositories.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tuxdevelop.spring_data_repositories.configuration.PersistenceConfiguration;
import org.tuxdevelop.spring_data_repositories.domain.Contact;
import org.tuxdevelop.spring_data_repositories.domain.Customer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    /*
     * Added at Part 4, to delete referenced Customers
     */
    @Autowired
    private OrderCrudRepository orderCrudRepository;
    /*
     * SIMPLE FINDER - Part 1
     */

    @Test
    public void findOneIT() {
        final Customer customer = customerRepository.findOne(1L);
        Assert.assertNotNull(customer);
        Assert.assertEquals("Donnie", customer.getFirstName());
        Assert.assertEquals("Darko", customer.getLastName());
    }

    @Test
    public void findByFirstNameIT() {
        final List<Customer> customers = customerRepository.findByFirstName("Frank");
        Assert.assertNotNull(customers);
        Assert.assertEquals(1, customers.size());
        final Customer customer = customers.get(0);
        Assert.assertNotNull(customer);
        Assert.assertEquals("Frank", customer.getFirstName());
    }

    /*
     * CRUD - Part 2
     */

    //ADD

    @Test
    public void saveCustomerIT() {
        final Customer customer = new Customer();
        customer.setFirstName("Darth");
        customer.setLastName("Vader");
        final Contact contact = new Contact();
        contact.setZipCode("99999");
        contact.setEmailAddress("vader@empire.com");
        contact.setStreetLine("Bridge");
        contact.setCity("Executor");
        customer.setContact(contact);

        final Customer savedCustomer = customerRepository.save(customer);

        Assert.assertNotNull(savedCustomer);
        Assert.assertEquals("Darth", savedCustomer.getFirstName());
        Assert.assertEquals("Vader", savedCustomer.getLastName());
        final Contact savedContact = customer.getContact();
        Assert.assertNotNull(savedContact);
        Assert.assertEquals("vader@empire.com", savedContact.getEmailAddress());
    }

    // UPDATE

    @Test
    public void saveCustomerMergeIT() {
        final Customer customer = customerRepository.findOne(1L);
        Assert.assertNotNull(customer);
        customer.getContact().setEmailAddress("donnie@empire.com");
        final Customer updatedCustomer = customerRepository.save(customer);
        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals("donnie@empire.com", updatedCustomer.getContact().getEmailAddress());
        final Customer fetchedCustomer = customerRepository.findOne(1L);
        Assert.assertEquals(updatedCustomer, fetchedCustomer);
    }

    // DELETE

    @Test
    public void deleteCustomerIT() {
        final Customer customer = customerRepository.findOne(3L);
        Assert.assertNotNull(customer);
        customerRepository.delete(customer);
        final Customer deletedCustomer = customerRepository.findOne(3L);
        Assert.assertNull(deletedCustomer);
        final long count = customerRepository.count();
        Assert.assertEquals(2, count);
    }

    @Test
    public void deleteAllIT() {
        orderCrudRepository.deleteAll();
        customerRepository.deleteAll();
        final long count = customerRepository.count();
        Assert.assertEquals(0, count);
    }

    // GET

    @Test
    public void findAllIT() {
        final List<Customer> customers = customerRepository.findAll();
        Assert.assertEquals(3, customers.size());
    }

    @Test
    public void findAllWithInIdsIT() {
        final List<Long> ids = new LinkedList<>();
        ids.add(1L);
        ids.add(3L);
        final List<Customer> customers = customerRepository.findAll(ids);
        Assert.assertEquals(2, customers.size());
    }

    /*
     * PAGING AND SORTING - Part 3
     */

    @Test
    public void findAllPage(){
        final Page<Customer> customerPage = customerRepository.findAll(new PageRequest(0,2));
        final int size = customerPage.getSize();
        Assert.assertEquals(2,size);
        Assert.assertTrue(customerPage.isFirst());
        Assert.assertTrue(customerPage.hasNext());
        Assert.assertFalse(customerPage.isLast());
    }

    @Test
    public void findAllPageWithSort(){
        final Page<Customer> customerPage = customerRepository.findAll(new PageRequest(0,2,new Sort(Sort.Direction
                .ASC,"firstName")));
        final int size = customerPage.getSize();
        Assert.assertEquals(2,size);
        final List<Customer> customers = customerPage.getContent();
        final Customer firstCustomer = customers.get(0);
        final Customer secondCustomer = customers.get(1);
        Assert.assertEquals("Donnie",firstCustomer.getFirstName());
        Assert.assertEquals("Frank",secondCustomer.getFirstName());
    }

    @Test
    public void finalAllSort(){
        final Iterable<Customer> customers = customerRepository.findAll(new Sort(Sort.Direction.DESC, "lastName"));
        final Iterator<Customer> iterator = customers.iterator();
        final Customer firstCustomer = iterator.next();
        Assert.assertEquals("Torvalds",firstCustomer.getLastName());
        final Customer secondCustomer = iterator.next();
        Assert.assertEquals("Drebin",secondCustomer.getLastName());
        final Customer thirdCustomer = iterator.next();
        Assert.assertEquals("Darko",thirdCustomer.getLastName());
    }

     /*
     * CUSTOMIZED QUERIES - Part 3
     */

    @Test
    public void findCustomersByContactCityIT(){
        final List<Customer> customers = customerRepository.findCustomersByContactCity("DonnieTown");
        Assert.assertEquals(1,customers.size());
    }

    @Test
    public void findCustomersByCityIT(){
        final List<Customer> customers = customerRepository.findCustomersByCity("DonnieTown");
        Assert.assertEquals(1,customers.size());
    }

    @Test
    public void findCustomersByCityNativeIT(){
        final List<Customer> customers = customerRepository.findCustomerByCityNative("DonnieTown");
        Assert.assertEquals(1,customers.size());
    }
}
