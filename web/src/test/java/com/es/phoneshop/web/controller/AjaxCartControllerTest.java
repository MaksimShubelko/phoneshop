package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.dto.CartItemDto;
import com.es.phoneshop.web.controller.validator.CartItemDaoValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
@WebAppConfiguration
public class AjaxCartControllerTest {

    private MockMvc mvc;
    @Mock
    private CartService cartService;
    @InjectMocks
    private AjaxCartController ajaxCartController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(ajaxCartController)
                .setValidator(new CartItemDaoValidator())
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();
        Cart cart = mock(Cart.class);
        when(cartService.getCart()).thenReturn(cart);
    }

    @Test
    public void addPhone_ok() throws Exception {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPhoneId(1000L);
        cartItemDto.setQuantity(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(cartItemDto);

        mvc.perform(post("/ajaxCart")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addPhone_bad_req() throws Exception {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPhoneId(1000L);
        cartItemDto.setQuantity(0L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(cartItemDto);

        mvc.perform(post("/ajaxCart")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}