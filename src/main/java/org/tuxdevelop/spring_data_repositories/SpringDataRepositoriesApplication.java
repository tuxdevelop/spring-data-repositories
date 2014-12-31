package org.tuxdevelop.spring_data_repositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class SpringDataRepositoriesApplication {

    public static void main(final String[] args){
        SpringApplication.run(SpringDataRepositoriesApplication.class,args);
    }

}
