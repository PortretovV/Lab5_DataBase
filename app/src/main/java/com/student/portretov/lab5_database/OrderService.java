package com.student.portretov.lab5_database;

import java.util.List;

/**
 * Created by adminvp on 9/8/17.
 */

public interface OrderService<ID> {

    long createOrder(Order order);

    List<Order> findOrderList();

    int deleteOrder(ID orderId);

    int updateOrder(Order order);

}
