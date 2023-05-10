package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderItemsResultSetExtractor implements ResultSetExtractor<List<OrderItem>> {
    @Override
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = null;
        Phone phone;
        Set<Color> colors = null;
        while (resultSet.next()) {
            if (orderItem == null || orderItem.getId() != resultSet.getLong("id")) {
                orderItem = new OrderItem();
                phone = new Phone();
                colors = new HashSet<>();
                initOrderItemFields(orderItem, resultSet);
                initPhoneFields(phone, resultSet);
                phone.setColors(colors);
                orderItem.setPhone(phone);
                orderItems.add(orderItem);
            }

            if (resultSet.getLong("colorId") != 0) {
                Color color = new Color();
                color.setId(resultSet.getLong("colorId"));
                color.setCode(resultSet.getString("colorCode"));
                colors.add(color);
            }
        }
        return orderItems;
    }

    private void initOrderItemFields(OrderItem orderItem, ResultSet resultSet) throws SQLException {
        orderItem.setId(resultSet.getLong("id"));
        Order order = new Order();
        order.setId(resultSet.getLong("orderId"));
        orderItem.setOrder(order);
        orderItem.setQuantity(resultSet.getLong("quantity"));
    }

    private void initPhoneFields(Phone phone, ResultSet resultSet) throws SQLException {
        phone.setId(resultSet.getLong("phoneId"));
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
        phone.setPrice(resultSet.getBigDecimal("price"));
    }
}
