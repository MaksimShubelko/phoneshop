package com.es.core.dao.order;

import com.es.core.model.order.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderResultSetExtractor implements ResultSetExtractor<Order> {
    @Override
    public Order extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Order order = new Order();
        if (resultSet.next()) {
            initOrderFields(order, resultSet);
        }

        return order;
    }

    private void initOrderFields(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getObject("id", UUID.class));
        order.setSerialNo(resultSet.getLong("serialNo"));
        order.setSubtotal(resultSet.getBigDecimal("subtotal"));
        order.setDeliveryPrice(resultSet.getBigDecimal("deliveryPrice"));
        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
        order.setFirstName(resultSet.getString("firstname"));
        order.setLastName(resultSet.getString("lastname"));
        order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
        order.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
        order.setStatus(resultSet.getString("status"));
        order.setAdditionalInf(resultSet.getString("additionalInf"));
    }
}