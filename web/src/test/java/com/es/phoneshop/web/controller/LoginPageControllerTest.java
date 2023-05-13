package com.es.phoneshop.web.controller;

import com.es.phoneshop.web.controller.pages.LoginPageController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context/applicationContext-web-test.xml")
public class LoginPageControllerTest {

    MockMvc mvc;

    @InjectMocks
    private LoginPageController loginPageController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(loginPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp"))
                .build();
    }

    @Test
    public void login() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}