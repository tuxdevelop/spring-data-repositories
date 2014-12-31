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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tuxdevelop.spring_data_repositories.configuration.PersistenceConfiguration;
import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.Order;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /*
     * PART 4 - Define own Base Repositories
     */

    @Test
    public void findOneIT() {
        final Order order = orderRepository.findOne(1L);
        Assert.assertNotNull(order);
        Assert.assertEquals("Bunny Mask", order.getDescription());
    }

    @Test
    public void findOrdersByCustomerIT() {
        final Customer customer = customerRepository.findOne(1L);
        final Iterable<Order> orders = orderRepository.findOrdersByCustomer(customer);
        final Iterator<Order> iterator = orders.iterator();
        final Order order = iterator.next();
        Assert.assertNotNull(order);
        Assert.assertEquals("Bunny Mask", order.getDescription());
        Assert.assertEquals(customer, order.getCustomer());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void findAllIT(){
        final Iterable<Order> orders = orderRepository.findAll();
        int count = 0;
        for(final Order order :orders){
            count++;
        }
        Assert.assertEquals(2,count);
    }

    @Test
    public void findAllSortIT(){
        final Iterable<Order> orders = orderRepository.findAll(new Sort(Sort.Direction.ASC,"price"));
        int count = 0;
        for(final Order order :orders){
            count++;
        }
        Assert.assertEquals(2,count);
        final Iterator<Order> iterator = orders.iterator();
        final Order firstOrder = iterator.next();
        final Order secondOrder = iterator.next();
        Assert.assertFalse(iterator.hasNext());
        Assert.assertEquals(new Double(19.49),firstOrder.getPrice());
        Assert.assertEquals(new Double(39.99),secondOrder.getPrice());
    }

    @Test
    public void findAllPage(){
        final Page<Order> orders = orderRepository.findAll(new PageRequest(0,1));
        Assert.assertTrue(orders.isFirst());
        Assert.assertTrue(orders.hasNext());
        final List<Order> orderList = orders.getContent();
        Assert.assertEquals(1,orderList.size());
    }

    @Test
    public void findAllIds(){
        final List<Long> ids = new LinkedList<>();
        ids.add(1L);
        final Iterable<Order> orders = orderRepository.findAll(ids);
        final Iterator<Order> iterator = orders.iterator();
        final Order order = iterator.next();
        Assert.assertEquals("Bunny Mask",order.getDescription());
        Assert.assertEquals(new Double(39.99),order.getPrice());
        Assert.assertFalse(iterator.hasNext());
    }


    /*
     * PART 6 - Custom Implementation
     */

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOrdersBeyondPriceIT(){
        orderRepository.deleteOrderBeyondPrice(20.00);
        Iterable<Order> orders = orderRepository.findAll();
        final Iterator<Order> iterator = orders.iterator();
        final Order currentOrder = iterator.next();
        Assert.assertFalse(currentOrder.getPrice()>20.00);
        Assert.assertFalse(iterator.hasNext());
    }

}
