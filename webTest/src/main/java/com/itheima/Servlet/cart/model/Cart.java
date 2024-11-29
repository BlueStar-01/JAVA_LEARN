package com.itheima.Servlet.cart.model;

import lombok.Data;

import java.util.Map;

@Data
public class Cart {
    private final Long id;
    private Long user_id;
    private Map<Integer, CartRow> cartMap;

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
