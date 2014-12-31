package org.tuxdevelop.spring_data_repositories.repository;


public interface OrderRepositoryExtension {

    void deleteOrderBeyondPrice(final Double price);

}
