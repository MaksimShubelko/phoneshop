package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
@WebAppConfiguration
public class ProductDetailsPageControllerTest {

    MockMvc mvc;
    @Mock
    private PhoneService phoneService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private ProductDetailsPageController productDetailsPageController;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(productDetailsPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();

        Phone phone = mock(Phone.class);
        Cart cart = mock(Cart.class);
        when(phoneService.getById(1010L)).thenReturn(phone);
        when(phone.getId()).thenReturn(1010L);
        when(cartService.getCart()).thenReturn(cart);
    }

    @Test
    public void showProductList() throws Exception {
        mvc.perform(get("/productDetails/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productDetails"));
    }
}