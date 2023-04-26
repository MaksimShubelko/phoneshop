package com.es.core.dao.stock;

import com.es.core.model.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class StockResultSetExtractorTest {

    private final static String GET_STOCK_BY_PHONE_ID = "SELECT * FROM stocks WHERE stocks.phoneId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StockResultSetExtractor resultSetExtractor;

    @Test
    public void extractData() {
        Stock stock = jdbcTemplate.query(GET_STOCK_BY_PHONE_ID, resultSetExtractor, 1001L);

        Long phoneId = stock.getPhone().getId();

        assertNotNull(phoneId);
        assertNotNull(stock.getStock());
        assertNotNull(stock.getReserved());
    }
}