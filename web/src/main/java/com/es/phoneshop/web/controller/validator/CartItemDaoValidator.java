package com.es.phoneshop.web.controller.validator;

import com.es.phoneshop.web.controller.dto.CartItemDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CartItemDaoValidator implements Validator {

    private static final String EMPTY_PHONE_ID_MESSAGE = "Unknown product";
    private static final String EMPTY_QUANTITY_MESSAGE = "Empty quantity";

    private static final String NEGATIVE_QUANTITY_MESSAGE = "Quantity should be positive";

    private static final String INCORRECT_INPUT_FORMAT_MESSAGE = "Incorrect input format";

    private static final String NEGATIVE_QUANTITY_ERR_CODE = "quantity.negative";

    private static final String INCORRECT_INPUT_ERR_CODE = "incorrect.input";


    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemDto cartItemDto = (CartItemDto) o;
        ValidationUtils.rejectIfEmpty(errors, "phoneId", EMPTY_PHONE_ID_MESSAGE);
        ValidationUtils.rejectIfEmpty(errors, "quantity", EMPTY_QUANTITY_MESSAGE);

        long quantity = cartItemDto.getQuantity();
        if (quantity < 1) {
            errors.rejectValue("quantity", NEGATIVE_QUANTITY_ERR_CODE, NEGATIVE_QUANTITY_MESSAGE);
        }

    }
}
