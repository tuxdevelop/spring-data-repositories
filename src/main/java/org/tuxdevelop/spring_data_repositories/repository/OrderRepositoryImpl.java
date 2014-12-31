package org.tuxdevelop.spring_data_repositories.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tuxdevelop.spring_data_repositories.domain.Order;
import org.tuxdevelop.spring_data_repositories.domain.QOrder;


public class OrderRepositoryImpl extends QueryDslRepositorySupport implements OrderRepositoryExtension{

    private static final QOrder order = QOrder.order;

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrderBeyondPrice(final Double price) {
        delete(order).where(order.price.gt(price)).execute();
    }
}
