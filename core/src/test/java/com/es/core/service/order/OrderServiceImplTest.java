package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.UnknownOrderException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.cart.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private StockDao stockDao;

    @Mock
    private CartService cartService;
    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void get() {
        Order order = mock(Order.class);
        when(orderDao.findById(any())).thenReturn(Optional.of(order));

        orderService.get(any());

        verify(orderDao, times(1)).findById(any());
    }

    @Test
    public void createOrder() {
        orderService.setDeliveryPrice(BigDecimal.TEN);
        Cart cart = mock(Cart.class);
        when(cart.getItems()).thenReturn(List.of(new CartItem(new Phone(), 1L)));
        when(cart.getTotalPrice()).thenReturn(BigDecimal.TEN);

        orderService.createOrder(cart);

        verify(cart, times(2)).getTotalPrice();
    }

    @Test
    public void placeOrder() {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(0L);
        Stock stock = mock(Stock.class);

        orderService.placeOrder(order);

        verify(orderDao, times(1)).save(order);
    }

    @Test
    public void findByUuid() {
        UUID uuid = UUID.randomUUID();
        Order order = mock(Order.class);
        when(orderDao.findByUuid(uuid)).thenReturn(Optional.of(order));

        orderService.findByUuid(uuid);

        verify(orderDao, times(1)).findByUuid(uuid);
    }

    @Test
    public void findAll() {
        orderService.findAll();

        verify(orderDao, times(1)).findAll();
    }

    @Test
    public void findById() {
        Long id = 1L;
        Order order = mock(Order.class);
        when(orderDao.findById(id)).thenReturn(Optional.of(order));

        orderService.findById(id);

        verify(orderDao, times(1)).findById(id);
    }

    @Test(expected = UnknownOrderException.class)
    public void updateStatus_exception() {
        orderService.updateStatus(null, OrderStatus.NEW);
    }

    @Test
    public void updateStatus_success() {
        Order order = mock(Order.class);
        OrderItem orderItem = mock(OrderItem.class);
        Phone phone = mock(Phone.class);
        Stock stock = mock(Stock.class);
        List<OrderItem> orderItems = new ArrayList<>(List.of(orderItem));
        when(order.getOrderItems()).thenReturn(orderItems);
        when(order.getId()).thenReturn(1L);
        when(orderItem.getPhone()).thenReturn(phone);
        when(phone.getId()).thenReturn(1L);
        when(stockDao.getByPhoneId(1L)).thenReturn(Optional.of(stock));

        orderService.updateStatus(order, OrderStatus.NEW);
    }
}