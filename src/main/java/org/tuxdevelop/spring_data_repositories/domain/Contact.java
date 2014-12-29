package org.tuxdevelop.spring_data_repositories.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String emailAddress;
	private String streetLine;
	private String zipCode;
	private String city;

}
