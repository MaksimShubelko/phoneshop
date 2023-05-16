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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class OrderDaoImplIT {

    private static final String INSERT_ORDER = "INSERT INTO orders (uuid, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, creationDate, status) VALUES " +
            "(:uuid, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :creationDate, :status)";

    @Resource
    private OrderDao orderDao;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Order order;

    private Long id;
    @Before
    public void setUp() throws Exception {
        order = new Order();
        UUID uuid = UUID.randomUUID();
        id = 1L;
        order.setUuid(uuid);
        order.setId(1L);
        order.setSubtotal(BigDecimal.ZERO);
        order.setDeliveryPrice(BigDecimal.ZERO);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setFirstName("firstname");
        order.setLastName("lastname");
        order.setDeliveryAddress("address");
        order.setContactPhoneNo("phoneNo");
        order.setStatus(OrderStatus.NEW.getStatus());
        order.setCreationDate(LocalDateTime.now());

        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsPhone);
    }

    @Test
    public void findById() {
        Optional<Order> orderOptional = orderDao.findById(id);

        assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getId(), id);
    }

    @Test
    public void save_existing() {
        order.setDeliveryPrice(BigDecimal.TEN);
        orderDao.save(order);
        Optional<Order> orderOptional = orderDao.findById(id);

        assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getDeliveryPrice(), BigDecimal.TEN);
    }

    @Test
    public void save_not_existing() {
        order.setId(null);
        orderDao.save(order);
        Optional<Order> orderOptional = orderDao.findById(id);
        Long actualSerialNo = orderOptional.map(Order::getId).orElseThrow(AssertionError::new);

        assertNotNull(orderOptional.get().getId());
        assertEquals(1L, (long) actualSerialNo);
    }

    @Test
    public void findAll() {
        List<Order> orders = orderDao.findAll();

        assertEquals(3, orders.size());
    }
}