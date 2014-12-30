package org.tuxdevelop.spring_data_repositories.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Contact contact;
}
