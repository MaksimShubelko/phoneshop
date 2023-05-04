package com.es.core.dao.stock;

import com.es.core.model.stock.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Component
public class StockDaoImpl implements StockDao {

    private final static String GET_STOCK_BY_PHONE_ID = "SELECT * FROM stocks WHERE stocks.phoneId = ?";

    private final static String INSERT_STOCK = "INSERT INTO stocks (stock, reserved, phoneId) VALUES " +
            "(?, ?, ?)";

    private final static String UPDATE_STOCK = "UPDATE stocks SET stock = ?, reserved = ? " +
            "WHERE stocks.phoneId = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ResultSetExtractor<Stock> stockResultSetExtractor;

    @Override
    public Optional<Stock> getByPhoneId(Long phoneId) {
        if (Objects.isNull(phoneId)) {
            return Optional.empty();
        }

        Stock stock = jdbcTemplate.query(GET_STOCK_BY_PHONE_ID, stockResultSetExtractor, phoneId);

        return Optional.ofNullable(stock);
    }

    @Override
    public void save(Stock stock) {
        Optional<Stock> optionalStock = getByPhoneId(stock.getPhone().getId());
        if (optionalStock.isPresent()) {
            update(stock);
        } else {
            insert(stock);
        }
    }

    private void insert(Stock stock) {
        jdbcTemplate.update(INSERT_STOCK, getNewParams(stock));
    }

    private void update(Stock stock) {
        jdbcTemplate.update(UPDATE_STOCK, getNewParams(stock));
    }

    private Object[] getNewParams(Stock stock) {
        return new Object[]{stock.getStock(), stock.getReserved(), stock.getPhone().getId()};
    }
}
