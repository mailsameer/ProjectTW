package com.thoughtworks.sameer.projectthoughtworks.data;

/**
 * Created by sameer on 9/25/2016.
 */

/**
 * This is a cart item, which is shown in a cart list.
 */
public class CartItem extends ShopItem {
    private int cartId;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
