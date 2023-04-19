package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;


    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        //TODO quantity checking, exception throwing
        if (quantity > 0) {
            Optional<Phone> phoneOptional = phoneDao.get(phoneId);
            if (phoneOptional.isPresent()) {
                Phone phone = phoneOptional.get();
                Optional<CartItem> cartItem = getItemFromCart(phone);

                if (cartItem.isPresent()) {
                    addItemToCart(cartItem.get(), quantity);
                } else {
                    addItemToCart(new CartItem(phone, 0L), quantity);
                }
            }
        }
    }

    private void addItemToCart(CartItem cartItem, Long quantity) {
        //TODO Quantity validation including stock's value, exception throwing
        long totalQuantity = cartItem.getQuantity() + quantity;

        if (cartItem.getQuantity() == 0) {
            cart.getItems().add(cartItem);
        }
        cartItem.setQuantity(totalQuantity);
        updateCartInformation();
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private Optional<CartItem> getItemFromCart(Phone phone) {
        Long phoneId = phone.getId();
        return cart.getItems()
                .stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findFirst();
    }

    private void updateCartInformation() {
        cart.setTotalQuantity(cart.getItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum());
        cart.setTotalPrice(cart.getItems().stream()
                .map(it -> it.getPhone().getPrice()
                        .multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
