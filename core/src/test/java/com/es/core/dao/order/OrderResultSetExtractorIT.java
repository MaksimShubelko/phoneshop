package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderResultSetExtractorIT {

    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = %s";

    private static final String INSERT_ORDER = "INSERT INTO orders (id, serialNo, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:id, :serialNo, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ResultSetExtractor<Order> orderResultSetExtractor;

    private Order order;

    private UUID uuid;

    @Before
    public void setUp() throws Exception {
        orderResultSetExtractor = new OrderResultSetExtractor();

        order = new Order();
        uuid = UUID.randomUUID();
        order.setId(uuid);
        order.setSubtotal(BigDecimal.ZERO);
        order.setDeliveryPrice(BigDecimal.ZERO);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setFirstName("firstname");
        order.setLastName("lastname");
        order.setDeliveryAddress("address");
        order.setContactPhoneNo("phoneNo");
        order.setStatus(OrderStatus.NEW.getStatus());
        order.setAdditionalInf("additionalInf");
        order.setSerialNo(1L);

        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsPhone);
    }

    @Test
    public void extractData() {
        Order order = jdbcTemplate.query(String.format(FIND_ORDER_BY_ID, "'" + uuid + "'"), orderResultSetExtractor);

        assertNotNull(order.getId());
        assertNotNull(order.getSerialNo());
        assertNotNull(order.getSubtotal());
        assertNotNull(order.getDeliveryPrice());
        assertNotNull(order.getTotalPrice());
        assertNotNull(order.getStatus());
        assertNotNull(order.getLastName());
        assertNotNull(order.getDeliveryAddress());
        assertNotNull(order.getAdditionalInf());
    }
}