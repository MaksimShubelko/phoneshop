package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.controller.dto.mapper.order.OrderPopulator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
@WebAppConfiguration
public class OrderPageControllerTest {

    MockMvc mvc;

    @Mock
    private OrderService orderService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderPopulator orderPopulator;

    @InjectMocks
    private OrderPageController orderPageController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(orderPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();
    }

    @Test
    public void getOrder_success() throws Exception {
        Cart cart = mock(Cart.class);
        Order order = mock(Order.class);
        when(cartService.getCart()).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        when(order.getOrderItems()).thenReturn(orderItems);

        mvc.perform(get("/order")).andExpect(status().isOk())
                .andExpect(view().name("order"));
    }

    @Test
    public void getOrder_error() throws Exception {
        Cart cart = mock(Cart.class);
        Order order = mock(Order.class);
        when(cartService.getCart()).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        when(order.getOrderItems()).thenReturn(orderItems);


        mvc.perform(get("/order")).andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    public void placeOrder() throws Exception {
        Cart cart = mock(Cart.class);
        Order order = mock(Order.class);
        when(cartService.getCart()).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        when(order.getOrderItems()).thenReturn(orderItems);

        mvc.perform(post("/order")).andExpect(status().is3xxRedirection());
    }
}