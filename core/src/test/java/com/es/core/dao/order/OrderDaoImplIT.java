package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderDaoImplIT {

    private static final String INSERT_ORDER = "INSERT INTO orders (id, serialNo, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:id, :serialNo, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";

    @Resource
    private OrderDao orderDao;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Order order;

    private UUID uuid;
    @Before
    public void setUp() throws Exception {
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
        order.setSerialNo(1L);

        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsPhone);

    }

    @Test
    public void getById() {
        Optional<Order> orderOptional = orderDao.getById(uuid);

        assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getId(), uuid);
    }

    @Test
    public void save_existing() {
        order.setDeliveryPrice(BigDecimal.TEN);
        orderDao.save(order);
        Optional<Order> orderOptional = orderDao.getById(uuid);

        assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getDeliveryPrice(), BigDecimal.TEN);
    }

    @Test
    public void save_not_existing() {
        order.setId(null);
        orderDao.save(order);
        Optional<Order> orderOptional = orderDao.getById(order.getId());
        Long actualSerialNo = orderOptional.map(Order::getSerialNo).orElseThrow(AssertionError::new);

        assertNotNull(orderOptional.get().getId());
        assertEquals(2L, (long) actualSerialNo);
    }
}