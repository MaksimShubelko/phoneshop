package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.phone.SearchingParamObject;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceImplTest {

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private SearchingParamObject searchingParamObject;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    @Before
    public void setUp() throws Exception {
        phoneService.setPhonesPerPage(7);
    }

    @Test
    public void findAll() {
        Phone phone = new Phone();
        List<Phone> phonesActual = new ArrayList<>(List.of(phone));
        when(phoneDao.findAll(searchingParamObject)).thenReturn(phonesActual);

        List<Phone> phonesExpected = phoneService.findAll(searchingParamObject);

        verify(phoneDao, Mockito.times(1)).findAll(searchingParamObject);
        verify(searchingParamObject, Mockito.times(1)).setOffset(anyInt());
        verify(searchingParamObject, Mockito.times(1)).setPhonesPerPage(anyInt());
        assertEquals(1, phonesExpected.size());
    }

    @Test
    public void getCountPages() {
        when(phoneDao.count(anyString())).thenReturn(7);

        int countPages = phoneService.getCountPages(anyString());

        verify(phoneDao, Mockito.times(1)).count(anyString());
        assertEquals(countPages, 1);
    }

    @Test
    public void getById_non_null() {
        Phone phone = new Phone();
        when(phoneDao.get(anyLong())).thenReturn(Optional.of(phone));

        Phone phoneActual = phoneService.getById(1L);

        verify(phoneDao, Mockito.times(1)).get(anyLong());
        assertEquals(phoneActual, phone);
    }

    @Test(expected = UnknownProductException.class)
    public void getById_null() {
        Phone phone = new Phone();

        Phone phoneActual = phoneService.getById(null);

        verify(phoneDao, Mockito.times(0)).get(anyLong());
    }
}