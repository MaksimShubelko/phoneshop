package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.InvalidQuantityException;
import com.es.core.exception.OutOfStockException;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    private final Cart cart;

    private final PhoneDao phoneDao;

    private final StockDao stockDao;

    public HttpSessionCartService(Cart cart, PhoneDao phoneDao, StockDao stockDao) {
        this.cart = cart;
        this.phoneDao = phoneDao;
        this.stockDao = stockDao;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityException();
        }

        Phone phone = phoneDao.get(phoneId).orElseThrow(UnknownProductException::new);

        Optional<CartItem> cartItem = getItemFromCart(phone);
        if (cartItem.isPresent()) {
            addItemToCart(cartItem.get(), quantity);
        } else {
            addItemToCart(new CartItem(phone, 0L), quantity);
        }
    }

    private void addItemToCart(CartItem cartItem, Long quantity) {
        long totalQuantity = cartItem.getQuantity() + quantity;
        long availableQuantity = stockDao.getByPhoneId(cartItem.getPhone().getId())
                .map(Stock::getStock)
                .orElse(0);

        if (availableQuantity < totalQuantity) {
            throw new OutOfStockException();
        }

        if (cartItem.getQuantity() == 0) {
            cart.getItems().add(cartItem);
        }
        cartItem.setQuantity(totalQuantity);
        updateCartInformation();
    }

    @Override
    public void update(Map<Long, Long> items) {
        items.forEach(this::updateItem);
    }

    private void updateItem(Long phoneId, Long quantity) {
        Integer availableQuantity = stockDao.getByPhoneId(phoneId)
                .map(Stock::getStock)
                .orElse(0);

        if (availableQuantity > quantity) {
            Optional<CartItem> cartItem = cart.getItems().stream()
                    .filter(i -> i.getPhone().getId().equals(phoneId))
                    .findFirst();

            cartItem.ifPresent(item -> item.setQuantity(quantity));
        }
        updateCartInformation();
    }

    @Override
    public void remove(Long phoneId) {
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(it -> it.getPhone().getId().equals(phoneId))
                .findFirst()
                .orElseThrow(UnknownProductException::new);

        cart.getItems().remove(cartItem);
        updateCartInformation();
    }

    @Override
    public void clear() {
        cart.setTotalQuantity(0L);
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setItems(Collections.emptyList());
    }

    private Optional<CartItem> getItemFromCart(Phone phone) {
        return cart.getItems()
                .stream()
                .filter(item -> item.getPhone().getId().equals(phone.getId()))
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
