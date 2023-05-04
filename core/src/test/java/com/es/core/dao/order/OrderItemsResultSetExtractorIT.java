package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
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
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderItemsResultSetExtractorIT {

    private static final String INSERT_ORDER = "INSERT INTO orders (id, serialNo, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:id, :serialNo, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";

    private static final String INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) " +
            "VALUES (?, ?, ?)";

    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT * FROM orderItems WHERE orderId = %s";


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ResultSetExtractor<List<OrderItem>> orderItemResultSetExtractor;

    private Order order;

    private UUID uuid;

    @Before
    public void setUp() throws Exception {
        orderItemResultSetExtractor = new OrderItemsResultSetExtractor();

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
        jdbcTemplate.update(INSERT_ORDER_ITEM, 1001L, uuid, 1);
    }

    @Test
    public void extractData() {
        List<OrderItem> orderItems = jdbcTemplate.query(String.format(FIND_ITEMS_BY_ORDER_ID, "'" + uuid + "'"), orderItemResultSetExtractor);

        assertEquals(1, orderItems.size());
        assertEquals(uuid, orderItems.get(0).getOrder().getId());
        assertEquals(1001L, (long) orderItems.get(0).getPhone().getId());
        assertEquals(1, (long) orderItems.get(0).getQuantity());
    }
}