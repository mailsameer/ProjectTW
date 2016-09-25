package com.thoughtworks.sameer.projectthoughtworks.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.data.CartItem;
import com.thoughtworks.sameer.projectthoughtworks.data.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by sameer on 9/25/2016.
 */

/**
 * Adapter class for displaying Cart List.
 */
public class CartListAdapter extends ArrayAdapter<CartItem> {
    private Context context;
    private ArrayList<CartItem> itemsList;
    private LayoutInflater inflater;
    private OnDataSetChangeListener listener;

    public CartListAdapter(Context context, ArrayList<CartItem> items) {
        super(context, 0, items);
        this.context = context;
        this.itemsList = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnDataSetChangedListener(OnDataSetChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        CartItem shopItem = (CartItem) itemsList.get(position);
        v = inflater.inflate(R.layout.cart_item, null);

        TextView txtName = (TextView) v.findViewById(R.id.product_name);
        TextView txtPrice = (TextView) v.findViewById(R.id.product_price);

        ImageView imgRemove = (ImageView) v.findViewById(R.id.btnRemove);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItem item = itemsList.get(position);
                removeItem(item, position);
            }
        });

        txtName.setText(shopItem.getTitle());
        txtPrice.setText(shopItem.getPrice());

        return v;
    }

    private void removeItem(CartItem cartItem, int position) {
        final CartItem item = cartItem;
        new AlertDialog.Builder(context).setTitle(R.string.delete_entry).setMessage(R.string.delete_confirmation)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper.getDatabaseInstance(context).deleteItemFromCart(item.getCartId());
                CartListAdapter.this.remove(item);
                notifyDataSetChanged();
            }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();

    }

    public interface OnDataSetChangeListener {
        void onDataSetChangeFinished();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener != null) {
            listener.onDataSetChangeFinished();
        }
    }
}
