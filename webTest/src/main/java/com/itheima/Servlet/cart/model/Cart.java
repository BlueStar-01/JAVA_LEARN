package com.itheima.Servlet.cart.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Cart {
    private final Long id;
    private Long user_id;
    private Map<Integer, CartRow> cartMap;

    public List<CartRow> getAllItems() {
        List<CartRow> cartRows = new ArrayList<>();
        for (CartRow cartRow : cartMap.values()) {
            cartRows.add(cartRow);
        }
        return cartRows;
    }

    @Data
    class CartRow {
        protected Item item;
        protected Integer number;

        protected Integer getPrice() {
            return item.getPrice() * number;
        }
    }

    public void addItem(Integer id, Integer number) {
        CartRow cartRow = new CartRow();
        cartMap.put(id, cartRow);
    }
}
