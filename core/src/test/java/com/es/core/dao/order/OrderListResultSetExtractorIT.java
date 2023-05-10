package com.es.core.dao.order;

import com.es.core.model.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderListResultSetExtractorIT {

    private static final String FIND_ALL_ORDERS = "SELECT * FROM orders";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ResultSetExtractor<List<Order>> orderListResultSetExtractor;

    @Test
    public void extractData() {
        List<Order> orderList = jdbcTemplate.query(FIND_ALL_ORDERS, orderListResultSetExtractor);

        assertFalse(orderList.isEmpty());

        for (Order order : orderList) {
            assertNotNull(order.getId());
            assertNotNull(order.getSerialNo());
            assertNotNull(order.getFirstName());
            assertNotNull(order.getLastName());
            assertNotNull(order.getContactPhoneNo());
            assertNotNull(order.getDeliveryAddress());
            assertNotNull(order.getTotalPrice());
            assertNotNull(order.getStatus());
            assertNotNull(order.getDate());
        }
    }
}