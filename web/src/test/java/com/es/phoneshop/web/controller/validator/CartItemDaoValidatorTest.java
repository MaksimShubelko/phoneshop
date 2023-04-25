package com.es.phoneshop.web.controller.validator;

import com.es.phoneshop.web.controller.dto.CartItemDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDaoValidatorTest {

    private Validator cartItemDaoValidator;

    @Before
    public void setUp() throws Exception {
        cartItemDaoValidator = new CartItemDaoValidator();
    }

    @Test
    public void validate_invalid_quantity() {
        CartItemDto cartItemDto = mock(CartItemDto.class);
        Errors errors = mock(Errors.class);
        when(cartItemDto.getQuantity()).thenReturn(0L);

        cartItemDaoValidator.validate(cartItemDto, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    public void validate_valid_quantity() {
        CartItemDto cartItemDto = mock(CartItemDto.class);
        Errors errors = mock(Errors.class);
        when(cartItemDto.getQuantity()).thenReturn(1L);

        cartItemDaoValidator.validate(cartItemDto, errors);

        verify(errors, times(0)).rejectValue(anyString(), anyString(), anyString());
    }
}