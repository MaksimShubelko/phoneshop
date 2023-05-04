package com.es.core.dao.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";

    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT * FROM orderItems WHERE orderItems.orderId = ?";

    private static final String UPDATE_ORDER = "UPDATE orders SET orders.subtotal = :subtotal, orders.deliveryPrice = :deliveryPrice, " +
            "orders.totalPrice = :totalPrice, orders.firstName = :firstName, orders.lastName = :lastName, orders.contactPhoneNo = :contactPhoneNo, " +
            "additionalInf = :additionalInf, orders.status = :status";

    private static final String INSERT_ORDER = "INSERT INTO orders (id, serialNo, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:id, :serialNo, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";

    private static final String SELECT_MAX_SERIAL_NO = "SELECT MAX(serialNo) FROM orders";

    private static final String INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) " +
            "VALUES (?, ?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private ResultSetExtractor<Order> orderResultSetExtractor;

    @Resource
    private ResultSetExtractor<List<OrderItem>> orderItemsResultSetExtractor;


    @Override
    public Optional<Order> getById(UUID uuid) {
        Order order = jdbcTemplate.query(FIND_ORDER_BY_ID, orderResultSetExtractor, uuid);
        List<OrderItem> orderItems = jdbcTemplate.query(FIND_ITEMS_BY_ORDER_ID,
                orderItemsResultSetExtractor, uuid);
        orderItems.forEach(item -> item
                .setPhone(phoneDao.get(item.getPhone().getId()).orElseThrow(UnknownProductException::new)));

        order.setOrderItems(orderItems);

        return Optional.of(order);
    }

    @Override
    public void save(Order order) {
        if (Objects.isNull(order.getId())) {
            insert(order);
            insertOrderItems(order);
        } else {
            update(order);
        }
    }

    private void update(Order order) {
        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(UPDATE_ORDER, namedParamsPhone);
    }

    private void insert(Order order) {
        Long maxSerialNo = jdbcTemplate.queryForObject(SELECT_MAX_SERIAL_NO, Long.class);
        if (Objects.isNull(maxSerialNo)) {
            maxSerialNo = 0L;
        }
        order.setSerialNo(++maxSerialNo);
        order.setId(UUID.randomUUID());
        SqlParameterSource namedParamsOrder = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsOrder);
    }

    private void insertOrderItems(Order order) {
        order.getOrderItems()
                .forEach(it -> jdbcTemplate.update(INSERT_ORDER_ITEM, it.getPhone().getId(), it.getOrder().getId(), it.getQuantity()));
    }

}
