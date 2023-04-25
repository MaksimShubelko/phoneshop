package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.InvalidQuantityException;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    private Cart cart;

    @Mock
    private PhoneDao phoneDao;
    @Mock
    private StockDao stockDao;

    @InjectMocks
    private HttpSessionCartService cartService;


    @Test
    public void getCart() {
        Cart cartActual = cartService.getCart();

        assertEquals(cart, cartActual);
    }

    @Test(expected = InvalidQuantityException.class)
    public void addPhone_invalid_quantity() {
        cartService.addPhone(1L, 0L);
    }

    @Test(expected = UnknownProductException.class)
    public void addPhone_invalid_phone_id() {
        when(phoneDao.get(anyLong())).thenReturn(Optional.empty());

        cartService.addPhone(0L, 1L);

        verify(phoneDao, times(1)).get(0L);
    }

    @Test
    public void addPhone_success() {
        Phone phone = mock(Phone.class);
        CartItem cartItem = mock(CartItem.class);
        List cartItemList = mock(List.class);
        Stock stock = mock(Stock.class);

        when(phone.getId()).thenReturn(0L);
        when(cart.getItems()).thenReturn(cartItemList);
        when(phoneDao.get(anyLong())).thenReturn(Optional.of(phone));
        when(stockDao.getByPhoneId(0L)).thenReturn(Optional.of(stock));
        when(stock.getStock()).thenReturn(10);
        when(cartItem.getQuantity()).thenReturn(1L);

        cartService.addPhone(0L, 1L);

        verify(phoneDao, times(1)).get(0L);
        verify(cart, times(4)).getItems();
        verify(phoneDao, times(1)).get(anyLong());
        verify(stockDao, times(1)).getByPhoneId(anyLong());
    }

    @Test
    public void update() {
        Map map = mock(Map.class);
        Stock stock = mock(Stock.class);
        when(stockDao.getByPhoneId(0L)).thenReturn(Optional.of(stock));

        cartService.update(map);

        verify(map, times(1)).forEach(any());
    }

    @Test(expected = UnknownProductException.class)
    public void remove_ex() {
        when(cart.getItems()).thenReturn(List.of());

        cartService.remove(0L);
    }

    @Test
    public void remove() {
        Phone phone = new Phone();
        phone.setId(0L);
        List<CartItem> cartItemList = new ArrayList<>(List.of(new CartItem(phone, 0L)));
        CartItem cartItem = mock(CartItem.class);
        when(cart.getItems()).thenReturn(cartItemList);
        when(cartItem.getPhone()).thenReturn(phone);

        cartService.remove(0L);

        verify(cart, times(4)).getItems();
    }
}