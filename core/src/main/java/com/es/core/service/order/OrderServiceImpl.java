package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.stock.StockDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.stock.Stock;
import com.es.core.service.cart.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter
@Service
public class OrderServiceImpl implements OrderService {

    private final StockDao stockDao;

    private final OrderDao orderDao;

    private final CartService cartService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Order get(UUID uuid) {
        return orderDao.getById(uuid).orElseThrow();
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        cart.getItems().forEach(
                it -> order.getOrderItems().add(new OrderItem(it.getPhone(), order, it.getQuantity())));
        order.setTotalPrice(cart.getTotalPrice().add(deliveryPrice));
        order.setDeliveryPrice(deliveryPrice);
        order.setSubtotal(cart.getTotalPrice());

        return order;
    }

    @Transactional
    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        checkStockForOrderItems(order);
        orderDao.save(order);
    }

    private void checkStockForOrderItems(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItem> outOfStock = new ArrayList<>();
        resolveOutOfStockItems(orderItems, outOfStock);

        if (!outOfStock.isEmpty()) {
            order.setOrderItems(orderItems);
            removeItemsFromCart(outOfStock);
            String message = outOfStock.stream()
                    .map(oi -> oi.getPhone().getModel())
                    .collect(Collectors.joining(", "));
            throw new OutOfStockException("Too much quantity and was removed models: " + message);
        } else {
            cartService.clear();
        }
    }

    private void resolveOutOfStockItems(List<OrderItem> orderItems, List<OrderItem> outOfStock) {
        orderItems.forEach(oi -> {
            Long quantity = oi.getQuantity();
            Integer stock = stockDao.getByPhoneId(oi.getPhone().getId())
                    .map(Stock::getStock).orElse(0);
            if (quantity > stock) {
                outOfStock.add(oi);
            }
        });
        orderItems.removeAll(outOfStock);
        orderItems.forEach(this::updateStock);
    }

    private void updateStock(OrderItem orderItem) {
        Stock stock = stockDao.getByPhoneId(orderItem.getPhone().getId())
                .orElseThrow(UnknownProductException::new);

        stock.setStock((int) (stock.getStock() - orderItem.getQuantity()));
        stock.setReserved((int) (stock.getReserved() + orderItem.getQuantity()));

        stockDao.save(stock);
    }

    private void removeItemsFromCart(List<OrderItem> outOfStock) {
        outOfStock
                .forEach(item -> cartService.getCart()
                        .getItems()
                        .removeIf(cartItem -> cartItem.getPhone().getId().equals(item.getPhone().getId())));
    }
}
