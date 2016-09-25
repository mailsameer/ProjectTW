package com.thoughtworks.sameer.projectthoughtworks.data;

/**
 * Created by sameer on 9/24/2016.
 */

/**
 * Section header list item for Product List View.
 */
public class SectionHeader implements ListItem {

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) { this.title = title; }

    @Override
    public boolean isSection() {
        return true;
    }


}
