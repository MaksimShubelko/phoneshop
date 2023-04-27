package com.es.core.dao.stock;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class StockDaoImplIT {

    @Resource
    private StockDao stockDao;

    @Resource
    private PhoneDao phoneDao;

    @Test
    public void getByPhoneId_found() {
        Optional<Stock> stock = stockDao.getByPhoneId(1000L);

        assertTrue(stock.isPresent());
    }

    @Test
    public void getByPhoneId_not_found() {
        Optional<Stock> stock = stockDao.getByPhoneId(Long.MAX_VALUE);

        assertNull(stock.orElseThrow(AssertionError::new).getStock());
    }

    @Test
    public void getByPhoneId_not_found_null_input() {
        Optional<Stock> stock = stockDao.getByPhoneId(null);

        assertTrue(stock.isEmpty());
    }

    @Test
    public void save_not_exists() {
        Stock stock = new Stock();
        Phone phone = new Phone();
        phone.setBrand("brand");
        phone.setModel("model");

        stock.setStock(0);
        stock.setReserved(0);

        phoneDao.save(phone);
        stock.setPhone(phone);
        Long generatedId = phone.getId();
        stockDao.save(stock);

        Optional<Stock> stockOptional = stockDao.getByPhoneId(generatedId);

        assertTrue(stockOptional.isPresent());
    }

    @Test
    public void save_exists() {
        Optional<Stock> stockOptional = stockDao.getByPhoneId(1001L);
        Stock stock = stockOptional.orElseThrow(AssertionError::new);

        stock.setStock(1000);
        stockDao.save(stock);

        int actualStock = stockDao.getByPhoneId(1001L).orElseThrow(AssertionError::new).getStock();

        assertEquals(actualStock, 1000);
    }
}