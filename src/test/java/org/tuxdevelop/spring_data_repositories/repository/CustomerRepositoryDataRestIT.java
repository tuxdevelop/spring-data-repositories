package org.tuxdevelop.spring_data_repositories.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.tuxdevelop.spring_data_repositories.SpringDataRepositoriesApplication;
import org.tuxdevelop.spring_data_repositories.domain.Contact;
import org.tuxdevelop.spring_data_repositories.domain.Customer;
import org.tuxdevelop.spring_data_repositories.domain.Order;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRepositoriesApplication.class)
@WebIntegrationTest(value = {"server.port=9009", "server.servlet-path=/repositories/"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerRepositoryDataRestIT {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URI = "http://localhost:9009/repositories/";
    private static final String CUSTOMERS_URI = BASE_URI + "customers/";

    //PART 7.1 - Spring Data Rest HAL_JSON

    //RestTemplate CRUD IT

    //GET
    @Test
    public void getCustomerIT() {
        //fetch the object
        final ResponseEntity<Customer> response = restTemplate.getForEntity(CUSTOMERS_URI + "1", Customer.class);
        final Customer customer = response.getBody();
        System.err.println(customer);
        assertThat(customer).isNotNull();
    }

    //POST
    @Test
    public void addCustomerIT() {
        final Customer customer = new Customer();
        customer.setLastName("Cox");
        customer.setFirstName("Carl");
        final Contact contact = new Contact();
        contact.setStreetLine("Intec Street");
        contact.setCity("London");
        contact.setEmailAddress("carl.cox@global.uk");
        contact.setZipCode("123456");
        customer.setContact(contact);
        final HttpEntity<Customer> httpEntity = new HttpEntity<>(customer);
        ResponseEntity<Customer> response = restTemplate.exchange(CUSTOMERS_URI, HttpMethod.POST, httpEntity, Customer.class);
        final Customer responseCustomer = response.getBody();
        assertThat(responseCustomer).isNotNull();
        final ResponseEntity<Customer> getReponse = restTemplate.getForEntity(CUSTOMERS_URI + "4", Customer.class);
        assertThat(getReponse).isNotNull();
        final Customer fetchedCustomer = getReponse.getBody();
        assertThat(fetchedCustomer).isNotNull();
        assertThat(fetchedCustomer.getFirstName()).isEqualTo("Carl");
    }

    //DELETE
    @Test
    public void deleteCustomerIT() {
        final Customer customer = new Customer();
        customer.setLastName("Cox");
        customer.setFirstName("Carl");
        final Contact contact = new Contact();
        contact.setStreetLine("Intec Street");
        contact.setCity("London");
        contact.setEmailAddress("carl.cox@global.uk");
        contact.setZipCode("123456");
        customer.setContact(contact);
        final HttpEntity<Customer> httpEntity = new HttpEntity<>(customer);
        ResponseEntity<Customer> response = restTemplate.exchange(CUSTOMERS_URI, HttpMethod.POST, httpEntity, Customer.class);
        final Customer responseCustomer = response.getBody();
        assertThat(responseCustomer).isNotNull();
        final ResponseEntity<Customer> getResponse = restTemplate.getForEntity(CUSTOMERS_URI + "4", Customer.class);
        assertThat(getResponse).isNotNull();
        final Customer fetchedCustomer = getResponse.getBody();
        assertThat(fetchedCustomer).isNotNull();
        assertThat(fetchedCustomer.getFirstName()).isEqualTo("Carl");
        restTemplate.delete(CUSTOMERS_URI + "4");
        try {
            restTemplate.getForEntity(CUSTOMERS_URI + "4", Customer.class);
            fail("customer not deleted!");
        } catch (HttpClientErrorException e) {
            assertThat(e.getMessage()).contains("404");
        }

    }

    //PUT
    @Test
    public void updateCustomerIT() {
        final Customer customer = new Customer();
        customer.setLastName("Cox");
        customer.setFirstName("Carl");
        final Contact contact = new Contact();
        contact.setStreetLine("Intec Street");
        contact.setCity("London");
        contact.setEmailAddress("carl.cox@global.uk");
        contact.setZipCode("123456");
        customer.setContact(contact);
        final HttpEntity<Customer> httpEntity = new HttpEntity<>(customer);
        ResponseEntity<Customer> response = restTemplate.exchange(CUSTOMERS_URI, HttpMethod.POST, httpEntity, Customer.class);
        final Customer responseCustomer = response.getBody();
        assertThat(responseCustomer).isNotNull();
        final ResponseEntity<Customer> getResponse = restTemplate.getForEntity(CUSTOMERS_URI + "4", Customer.class);
        assertThat(getResponse).isNotNull();
        final Customer fetchedCustomer = getResponse.getBody();
        assertThat(fetchedCustomer).isNotNull();
        assertThat(fetchedCustomer.getFirstName()).isEqualTo("Carl");
        fetchedCustomer.setFirstName("Big Carl");
        final HttpEntity<Customer> updateHttpEntity = new HttpEntity<>(fetchedCustomer);
        final ResponseEntity<Customer> updateResponse = restTemplate.exchange(CUSTOMERS_URI + "4", HttpMethod.PUT,
                updateHttpEntity, Customer.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getBody()).isEqualTo(fetchedCustomer);
        final ResponseEntity<Customer> getUpdateResponse = restTemplate.getForEntity(CUSTOMERS_URI + "4", Customer
                .class);
        assertThat(getUpdateResponse).isNotNull();
        assertThat(getUpdateResponse.getBody()).isNotNull();
        assertThat(getUpdateResponse.getBody().getFirstName()).isEqualTo("Big Carl");

    }

    //HAL_JSON IT

    @Test
    public void traversCustomersIT() {
        //Travers customers
        try {
            final Traverson traverson = new Traverson(new URI(BASE_URI), MediaTypes.HAL_JSON);
            final PagedResources<Resource<Customer>> resources = traverson
                    .follow("customers")
                    .toObject(new TypeReferences.PagedResourcesType<Resource<Customer>>() {
                    });
            assertThat(resources.getContent()).isNotNull();
            assertThat(resources.getContent().size()).isEqualTo(3);
            System.err.println(resources.getContent());
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void traversCustomersResourceIT() {
        //Travers customers
        try {
            final Traverson traverson = new Traverson(new URI(BASE_URI), MediaTypes.HAL_JSON);
            final PagedResources<Resource<Customer>> resources = traverson
                    .follow("customers")
                    .toObject(new TypeReferences.PagedResourcesType<Resource<Customer>>() {
                    });
            assertThat(resources.getContent()).isNotNull();
            assertThat(resources.getContent().size()).isEqualTo(3);
            System.err.println(resources.getMetadata());
            for (Resource<Customer> resource : resources) {
                final List<Link> links = resource.getLinks();
                System.err.println(links);
                final Customer customer = resource.getContent();
                System.err.println(customer);
            }
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void traversCustomerFindByFirstNameIT() {
        //travers to findByFirstName
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("firstName", "Donnie");
            final Traverson traverson = new Traverson(new URI(BASE_URI), MediaTypes.HAL_JSON);
            final PagedResources<Resource<Customer>> resources = traverson
                    .follow("customers", "search", "findByFirstName")
                    .withTemplateParameters(parameters)
                    .toObject(new TypeReferences.PagedResourcesType<Resource<Customer>>() {
                    });
            assertThat(resources.getContent()).isNotNull();
            assertThat(resources.getContent().size()).isEqualTo(1);
            final List<Resource<Customer>> customers = new LinkedList<>(resources.getContent());
            final Customer customer = customers.get(0).getContent();
            assertThat(customer).isNotNull();
            assertThat(customer.getFirstName()).isEqualTo("Donnie");
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }

    }

}
