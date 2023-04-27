package com.es.phoneshop.web.controller.validator;

import com.es.core.dao.stock.StockDao;
import com.es.core.model.stock.Stock;
import com.es.phoneshop.web.controller.dto.CartItemUpdatingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemUpdatingDaoValidatorTest {

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private CartItemUpdatingDaoValidator cartItemUpdatingDaoValidator;

    @Test
    public void validate_quantity_invalid() {
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 2L);
        Errors errors = mock(Errors.class);
        CartItemUpdatingDto dto = mock(CartItemUpdatingDto.class);
        when(dto.get()).thenReturn(map);

        cartItemUpdatingDaoValidator.validate(dto, errors);

        verify(errors, times(1)).rejectValue("cartItems['1']", "out.of.stock", "Out of stock");
    }

    @Test
    public void validate_quantity_negative() {
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, -1L);
        Errors errors = mock(Errors.class);
        CartItemUpdatingDto dto = mock(CartItemUpdatingDto.class);
        when(dto.get()).thenReturn(map);

        cartItemUpdatingDaoValidator.validate(dto, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    public void validate_quantity_valid() {
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 1L);
        Errors errors = mock(Errors.class);
        CartItemUpdatingDto dto = mock(CartItemUpdatingDto.class);
        Stock stock = new Stock();
        stock.setStock(2);
        when(dto.get()).thenReturn(map);
        when(stockDao.getByPhoneId(anyLong())).thenReturn(Optional.of(stock));

        cartItemUpdatingDaoValidator.validate(dto, errors);

        verify(errors, times(0)).rejectValue(anyString(), anyString(), anyString());
    }
}