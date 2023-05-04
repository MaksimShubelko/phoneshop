package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.cart.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    private OrderDao orderDao;

    @Mock
    private CartService cartService;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void get() {
        Order order = mock(Order.class);
        when(orderDao.getById(any())).thenReturn(Optional.of(order));

        orderService.get(any());

        verify(orderDao, times(1)).getById(any());
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
}