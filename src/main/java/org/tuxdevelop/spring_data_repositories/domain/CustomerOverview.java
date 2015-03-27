package org.tuxdevelop.spring_data_repositories.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="customerOverview",types = Customer.class)
public interface CustomerOverview {

    String getFirstName();
    String getLastName();
    @Value("#{target.contact.toString()}")
    String getAddressLine();

}
