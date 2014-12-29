package org.tuxdevelop.spring_data_repositories.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	@Column(name = "contact_id")
	@PrimaryKeyJoinColumn
	private Contact contact;
}
