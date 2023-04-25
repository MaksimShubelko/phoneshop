package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.SearchingParamObject;
import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneServiceImpl;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
@WebAppConfiguration
public class ProductListPageControllerTest {

    MockMvc mvc;
    @Mock
    private PhoneServiceImpl phoneService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private ProductListPageController productListPageController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(productListPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();
        SearchingParamObject searchingParamObject = mock(SearchingParamObject.class);
        List phones = mock(List.class);
        Cart cart = mock(Cart.class);
        when(phoneService.findAll(searchingParamObject)).thenReturn(phones);
        when(phoneService.getCountPages(anyString())).thenReturn(0);
        when(cartService.getCart()).thenReturn(cart);
    }

    @Test
    public void showProductList_ok() throws Exception {
        mvc.perform(get("/productList"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"));
    }
}