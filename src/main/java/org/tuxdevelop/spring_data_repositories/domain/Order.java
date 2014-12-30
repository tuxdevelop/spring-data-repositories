package org.tuxdevelop.spring_data_repositories.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "Orders")
public class Order extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    private String description;
    private Double price;
    @OneToOne
    private Customer customer;
}
