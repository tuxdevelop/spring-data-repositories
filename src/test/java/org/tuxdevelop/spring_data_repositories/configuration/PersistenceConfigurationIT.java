package org.tuxdevelop.spring_data_repositories.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PersistenceConfigurationIT {

	@Test
	public void initContextIT() {
		final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				PersistenceConfiguration.class);
		Assert.assertNotNull(applicationContext);
	}

}
