package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderItemsResultSetExtractor implements ResultSetExtractor<List<OrderItem>> {
    @Override
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<OrderItem> orderItems = new ArrayList<>();

        while (resultSet.next()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(resultSet.getLong("id"));
            Phone phone = new Phone();
            phone.setId(resultSet.getLong("phoneId"));
            orderItem.setPhone(phone);
            Order order = new Order();
            order.setId(resultSet.getObject("orderId", UUID.class));
            orderItem.setOrder(order);
            orderItem.setQuantity(resultSet.getLong("quantity"));
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
