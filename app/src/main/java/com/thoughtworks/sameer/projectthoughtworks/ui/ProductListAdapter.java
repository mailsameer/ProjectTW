package com.thoughtworks.sameer.projectthoughtworks.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.data.ListItem;
import com.thoughtworks.sameer.projectthoughtworks.data.SectionHeader;
import com.thoughtworks.sameer.projectthoughtworks.data.ShopItem;

import java.util.ArrayList;

/**
 * Created by sameer on 9/24/2016.
 */

/**
 * View Product List fragment.
 */
public class ProductListAdapter extends ArrayAdapter <ListItem> {
    private Context context;
    private ArrayList<ListItem> itemsList;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);
        this.context = context;
        this.itemsList = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final ListItem i = itemsList.get(position);
        if(i.isSection()) {
            SectionHeader header = (SectionHeader) i;
            v = inflater.inflate(R.layout.section_header_item, null);
            v.setOnClickListener(null);
            v.setOnLongClickListener(null);
            v.setLongClickable(false);
            TextView tv = (TextView) v.findViewById(R.id.list_item_section_text);
            tv.setText(header.getTitle());

        } else {
            ShopItem shopItem = (ShopItem) i;
            v = inflater.inflate(R.layout.shop_item, null);
            TextView tv = (TextView) v.findViewById(R.id.shopItemTitle);
            tv.setText(shopItem.getTitle());
        }
        return v;
    }

}
