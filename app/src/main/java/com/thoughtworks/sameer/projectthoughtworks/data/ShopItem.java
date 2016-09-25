package com.thoughtworks.sameer.projectthoughtworks.data;

/**
 * Created by sameer on 9/24/2016.
 */

/**
 * Product item from Product List View.
 */
public class ShopItem implements ListItem {
    private String id;
    private String title;
    private String category;
    private String price;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) { this.title = title; }

    @Override
    public boolean isSection() {
        return false;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
