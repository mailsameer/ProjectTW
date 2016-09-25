package com.thoughtworks.sameer.projectthoughtworks.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.data.CartItem;
import com.thoughtworks.sameer.projectthoughtworks.data.DatabaseHelper;
import com.thoughtworks.sameer.projectthoughtworks.data.ShopItem;
import com.thoughtworks.sameer.projectthoughtworks.utility.FragmentUtility;

/**
 * View Product details fragment.
 */
public class ProductDetailsFragment extends Fragment implements View.OnClickListener{

    public static String PRODUCT_ID = "id";

    private ShopItem productToShow;

    private ImageView productImage;
    private TextView productPrice;
    private TextView productName;
    private Button btnAddCart;

    private DatabaseHelper dbHelper;

    public ProductDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        String productID = args.getString(PRODUCT_ID, "1");
        dbHelper = DatabaseHelper.getDatabaseInstance(this.getActivity().getApplicationContext());
        productToShow = dbHelper.getItemBasedOnId(productID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.my_shop);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_shopping_cart : {
                CartListFragment cartFragment = new CartListFragment();
                FragmentUtility.addFragmentOnStack(this.getActivity(), R.id.fragment_frame, cartFragment, null);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);

        productImage = (ImageView) rootView.findViewById(R.id.imageView);
        View priceLayout = rootView.findViewById(R.id.item_price_layout);
        productPrice = (TextView) priceLayout.findViewById(R.id.shopItemDetail);
        TextView priceLabel = (TextView) priceLayout.findViewById(R.id.shopItemTitle);
        View nameLayout = rootView.findViewById(R.id.item_name_layout);
        productName = (TextView) nameLayout.findViewById(R.id.shopItemDetail);
        TextView nameLabel = (TextView) nameLayout.findViewById(R.id.shopItemTitle);
        btnAddCart = (Button) rootView.findViewById(R.id.buttonAddCart);
        btnAddCart.setOnClickListener(this);

        priceLabel.setText(R.string.price);
        nameLabel.setText(R.string.name);
        productImage.setImageResource(getImageId(productToShow.getId()));

        productPrice.setText(productToShow.getPrice());
        productName.setText(productToShow.getTitle());

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonAddCart : {
                CartItem cartItem = new CartItem();
                cartItem.setId(productToShow.getId());
                dbHelper.insertItemToCart(cartItem);
                Toast.makeText(this.getContext(), R.string.item_added, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Dummy method to select hardcoded image based on the item id.
     * @param product_id
     * @return
     */
    private int getImageId(String product_id) {
        int drawable = -1;
        switch (product_id) {
            case "1" : {
                drawable = R.drawable.microwave_oven;
                break;
            }
            case "2" : {
                drawable = R.drawable.television;
                break;
            }
            case "3" : {
                drawable = R.drawable.vacuum_cleaner;
                break;
            }
            case "4" : {
                drawable = R.drawable.table;
                break;
            }
            case "5" : {
                drawable = R.drawable.chair;
                break;
            }
            case "6" : {
                drawable = R.drawable.almirah;
                break;
            }
            default: {
                drawable = R.drawable.microwave_oven;
                break;
            }
        }
        return drawable;
    }


}
