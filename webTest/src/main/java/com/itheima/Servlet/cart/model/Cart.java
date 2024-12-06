package com.itheima.Servlet.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Cart {
    private final Long id;
    private Long user_id;
    private Map<Long, CartRow> cartMap = new HashMap<Long, CartRow>();

    public List<CartRow> getAllRow() {
        List<CartRow> cartRows = new ArrayList<>();
        for (CartRow cartRow : cartMap.values()) {
            cartRows.add(cartRow);
        }
        return cartRows;
    }

    public void removeItem(Long bookId) {
        cartMap.remove(bookId);
    }

    public void clean() {
        cartMap.clear();
    }

    @Data
    @AllArgsConstructor
    public class CartRow {
        protected Book item;
        protected Integer number;

        public Integer getPrice() {
            return item.getPrice() * number;
        }
    }

    public void addItem(Book item, Integer number) {

        if (!cartMap.containsKey(item.getId())) {
            CartRow row = new CartRow(item, number);
            cartMap.put(item.getId(), row);
        } else {
            CartRow row = cartMap.get(item.getId());
            row.setNumber(row.getNumber() + number);
        }

    }
}
