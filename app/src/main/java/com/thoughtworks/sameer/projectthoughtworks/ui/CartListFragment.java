package com.thoughtworks.sameer.projectthoughtworks.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.data.CartItem;
import com.thoughtworks.sameer.projectthoughtworks.data.DatabaseHelper;
import com.thoughtworks.sameer.projectthoughtworks.utility.FragmentUtility;

import java.util.ArrayList;

/**
 * View Shopping cart fragment.
 */
public class CartListFragment extends Fragment implements CartListAdapter.OnDataSetChangeListener {
    private CartListAdapter cartListAdapter = null;
    private ListView cartList = null;

    private ArrayList<CartItem> arrayCartItems = null;
    private TextView textTotal;

    public CartListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.shopping_cart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cart_list, container, false);
        arrayCartItems = DatabaseHelper.getDatabaseInstance(this.getContext()).getItemsInCart();
        textTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        recalculateTotal();
        if(arrayCartItems != null) {
            cartListAdapter = new CartListAdapter(this.getActivity(), arrayCartItems);
            cartListAdapter.setOnDataSetChangedListener(this);
            cartList = (ListView) rootView.findViewById(R.id.cart_list);
            cartList.setAdapter(cartListAdapter);

             cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CartItem cartItem = (CartItem) adapterView.getAdapter().getItem(i);
                    String id = cartItem.getId();
                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                    Bundle args = new Bundle();
                    args.putString(ProductDetailsFragment.PRODUCT_ID, id);
                    productDetailsFragment.setArguments(args);
                    FragmentUtility.addFragmentOnStack(CartListFragment.this.getActivity(), R.id.fragment_frame, productDetailsFragment, null);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onDataSetChangeFinished() {
        recalculateTotal();
    }

    private void recalculateTotal() {
        double totalPrice = 0.0;
        if(arrayCartItems != null) {
            for (int i = 0; i < arrayCartItems.size(); i++) {
                CartItem item = arrayCartItems.get(i);
                String price = item.getPrice();
                double d = Double.parseDouble(price);
                totalPrice += d;
            }
        }
        textTotal.setText(this.getString(R.string.total_price_text) + "" + String.format("%.2f", totalPrice));
    }
}
