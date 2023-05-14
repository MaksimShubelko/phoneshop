package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
@WebAppConfiguration
public class OrdersPageControllerTest {

    @Mock
    public OrderService orderService;

    @InjectMocks
    public OrdersPageController ordersPageController;

    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(ordersPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();
        Order order = mock(Order.class);
        when(orderService.findById(1L)).thenReturn(order);
    }

    @Test
    public void getOrders() throws Exception {
        mvc.perform(get("/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    public void getOrder() throws Exception {
        mvc.perform(get("/admin/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void changeStatus() throws Exception {
        mvc.perform(post("/admin/orders/1").param("status", String.valueOf(OrderStatus.DELIVERED)))
                .andExpect(status().is3xxRedirection());
    }
}