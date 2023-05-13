package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";

    private static final String FIND_ORDER_BY_UUID = "SELECT * FROM orders WHERE uuid = ?";

    private static final String FIND_ALL_ORDERS = "SELECT * FROM orders";

    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT o.*, p.id AS phoneId, p.model, p.brand, p.displaySizeInches, p.price, " +
            "colors.id AS colorId, colors.code AS colorCode FROM orderItems o " +
            "JOIN phones p ON o.phoneId = p.id " +
            "LEFT JOIN phone2color ON phone2color.phoneId = p.id " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId AND o.orderId = ?";

    private static final String UPDATE_ORDER = "UPDATE orders SET orders.subtotal = :subtotal, orders.deliveryPrice = :deliveryPrice, " +
            "orders.totalPrice = :totalPrice, orders.firstName = :firstName, orders.lastName = :lastName, orders.contactPhoneNo = :contactPhoneNo, " +
            "additionalInf = :additionalInf, orders.status = :status WHERE orders.id = :id";

    private static final String INSERT_ORDER = "INSERT INTO orders (uuid, subtotal, deliveryPrice, " +
            "totalPrice, firstName, lastName, deliveryAddress, contactPhoneNo, additionalInf, status) VALUES " +
            "(:uuid, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :additionalInf, :status)";
    private static final String INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) " +
            "VALUES (?, ?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Resource
    private ResultSetExtractor<Order> orderResultSetExtractor;

    @Resource
    private ResultSetExtractor<List<Order>> orderListResultSetExtractor;

    @Resource
    private ResultSetExtractor<List<OrderItem>> orderItemsResultSetExtractor;

    @Override
    public void save(Order order) {
        if (Objects.isNull(order.getId())) {
            insert(order);
            insertOrderItems(order);
        } else {
            update(order);
        }
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(FIND_ALL_ORDERS, orderListResultSetExtractor);
    }

    @Override
    public Optional<Order> findByUuid(UUID uuid) {
        Order order = jdbcTemplate.query(FIND_ORDER_BY_UUID, orderResultSetExtractor, uuid);
        setOrderItemsIntoOrder(order);

        return Optional.ofNullable(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = jdbcTemplate.query(FIND_ORDER_BY_ID, orderResultSetExtractor, id);
        setOrderItemsIntoOrder(order);

        return Optional.ofNullable(order);
    }

    private void setOrderItemsIntoOrder(Order order) {
        if (order != null && order.getUuid() != null) {
            List<OrderItem> orderItems = jdbcTemplate.query(FIND_ITEMS_BY_ORDER_ID,
                    orderItemsResultSetExtractor, order.getId());
            order.setOrderItems(orderItems);
        }
    }

    private void update(Order order) {
        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(UPDATE_ORDER, namedParamsPhone);
    }

    private void insert(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        order.setUuid(UUID.randomUUID());
        SqlParameterSource namedParamsOrder = new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(INSERT_ORDER, namedParamsOrder, keyHolder);
        order.setId(keyHolder.getKey().longValue());
    }

    private void insertOrderItems(Order order) {
        order.getOrderItems()
                .forEach(it -> jdbcTemplate.update(INSERT_ORDER_ITEM, it.getPhone().getId(), it.getOrder().getId(), it.getQuantity()));
    }

}
