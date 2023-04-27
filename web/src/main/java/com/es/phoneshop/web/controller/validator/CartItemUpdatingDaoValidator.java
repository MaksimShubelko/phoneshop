package com.es.phoneshop.web.controller.validator;

import com.es.core.dao.stock.StockDao;
import com.es.core.model.stock.Stock;
import com.es.phoneshop.web.controller.dto.CartItemUpdatingDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartItemUpdatingDaoValidator implements Validator {

    public static final String NEGATIVE_QUANTITY_MESSAGE = "Quantity should be positive";
    public static final String OUT_OF_STOCK_QUANTITY_MESSAGE = "Out of stock";

    public static final String NEGATIVE_QUANTITY_ERR_CODE = "negative.quantity";

    public static final String OUT_OF_STOCK_QUANTITY_ERR_CODE = "out.of.stock";

    @Resource
    private StockDao stockDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemUpdatingDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemUpdatingDto dto = (CartItemUpdatingDto) o;
        List<Long> phonesIdToRemove = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : dto.get().entrySet()) {
            Optional<Stock> stockOptional = stockDao.getByPhoneId(entry.getKey());
            Integer availableQuantity = 0;
            Long quantity = entry.getValue();

            if (stockOptional.isPresent()) {
                availableQuantity = stockOptional.get().getStock();
            }

            if (entry.getValue() > availableQuantity) {
                errors.rejectValue(String.format("cartItems['%s']", entry.getKey()), OUT_OF_STOCK_QUANTITY_ERR_CODE,
                        OUT_OF_STOCK_QUANTITY_MESSAGE);
                phonesIdToRemove.add(entry.getKey());
            }

            if (quantity < 1) {
                errors.rejectValue(String.format("cartItems['%s']", entry.getKey()), NEGATIVE_QUANTITY_ERR_CODE,
                        NEGATIVE_QUANTITY_MESSAGE);
                phonesIdToRemove.add(entry.getKey());
            }
        }
        phonesIdToRemove.forEach(phId -> dto.getCartItems().remove(phId));
    }
}
