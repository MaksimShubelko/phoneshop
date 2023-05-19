package com.es.phoneshop.web.controller.validator;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.InvalidQuantityException;
import com.es.core.exception.OutOfStockException;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.phoneshop.web.controller.dto.QuickOrderItemDto;
import com.es.phoneshop.web.controller.dto.QuickOrderItemsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddToCartValidator implements Validator {

    private final PhoneDao phoneDao;

    private final StockDao stockDao;

    private static final String EMPTY_MODEL_ERROR_CODE = "empty.model";

    private static final String OUT_OF_STOCK_ERROR_CODE = "out.of.stock";

    private static final String UNKNOWN_PRODUCT_ERROR_CODE = "unknown.product";

    private static final String INVALID_QUANTITY_ERROR_CODE = "invalid.quantity";

    @Override
    public boolean supports(Class<?> clazz) {
        return QuickOrderItemsDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QuickOrderItemsDto dto = (QuickOrderItemsDto) target;
        List<QuickOrderItemDto> items = dto.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPhoneModel().isEmpty()) {
                errors.rejectValue("items[" + i + "].phoneModel", EMPTY_MODEL_ERROR_CODE, "Model shouldn't be empty");
                continue;
            }

            try {
                validateQuantity(items.get(i));
            } catch (OutOfStockException exception) {
                errors.rejectValue("items[" + i + "].quantity", OUT_OF_STOCK_ERROR_CODE, "Out of stock");
            } catch (UnknownProductException exception) {
                errors.rejectValue("items[" + i + "].phoneModel", UNKNOWN_PRODUCT_ERROR_CODE, "Unknown product");
            } catch (InvalidQuantityException exception) {
                errors.rejectValue("items[" + i + "].quantity", INVALID_QUANTITY_ERROR_CODE, "Please, enter quantity");
            }
        }
    }

    private void validateQuantity(QuickOrderItemDto quickOrderItemDto) {
        Phone phone = phoneDao.findPhoneByModel(quickOrderItemDto.getPhoneModel())
                .orElseThrow(UnknownProductException::new);
        Long quantity = quickOrderItemDto.getQuantity();
        if (quantity == null) {
            throw new InvalidQuantityException();
        }

        Integer availableQuantity = stockDao.getByPhoneId(phone.getId())
                .map(Stock::getStock)
                .orElse(0);
        if (availableQuantity < quantity) {
            throw new OutOfStockException();
        }
        quickOrderItemDto.setHasErrors(false);
    }
}
