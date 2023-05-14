package com.es.core.dao.order;

import com.es.core.model.order.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderListResultSetExtractor implements ResultSetExtractor<List<Order>> {
    @Override
    public List<Order> extractData(ResultSet rs) throws DataAccessException, SQLException {
        List<Order> orders = new ArrayList<>();
        Order order;

        while (rs.next()) {
            order = new Order();
            initOrderFields(order, rs);
            orders.add(order);
        }

        return orders;
    }

    private void initOrderFields(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong("id"));
        order.setFirstName(resultSet.getString("firstname"));
        order.setLastName(resultSet.getString("lastname"));
        order.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
        order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
        order.setStatus(resultSet.getString("status"));
        order.setCreationDate(resultSet.getTimestamp("creationDate").toLocalDateTime());
    }
}
