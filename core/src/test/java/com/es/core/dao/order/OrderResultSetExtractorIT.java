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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderResultSetExtractorIT {

    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";

    private static final String INSERT_ORDER = "INSERT INTO orders (uuid, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:uuid, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ResultSetExtractor<Order> orderResultSetExtractor;

    private Order order;

    private UUID uuid;

    private Long id;

    @Before
    public void setUp() throws Exception {
        orderResultSetExtractor = new OrderResultSetExtractor();

        order = new Order();
        uuid = UUID.randomUUID();
        order.setUuid(uuid);
        order.setSubtotal(BigDecimal.ZERO);
        order.setDeliveryPrice(BigDecimal.ZERO);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setFirstName("firstname");
        order.setLastName("lastname");
        order.setDeliveryAddress("address");
        order.setContactPhoneNo("phoneNo");
        order.setStatus(OrderStatus.NEW.getStatus());
        order.setAdditionalInf("additionalInf");

        SqlParameterSource namedParamsOrder = new BeanPropertySqlParameterSource(order);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsOrder, generatedKeyHolder);
        id = generatedKeyHolder.getKey().longValue();
        order.setId(id);
    }

    @Test
    public void extractData() {
        Order order = jdbcTemplate.query(FIND_ORDER_BY_ID, orderResultSetExtractor, id);

        assertNotNull(order.getId());
        assertNotNull(order.getSubtotal());
        assertNotNull(order.getDeliveryPrice());
        assertNotNull(order.getTotalPrice());
        assertNotNull(order.getStatus());
        assertNotNull(order.getLastName());
        assertNotNull(order.getDeliveryAddress());
        assertNotNull(order.getAdditionalInf());
    }
}