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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.data.DatabaseHelper;
import com.thoughtworks.sameer.projectthoughtworks.data.ListItem;
import com.thoughtworks.sameer.projectthoughtworks.data.SectionHeader;
import com.thoughtworks.sameer.projectthoughtworks.data.ShopItem;
import com.thoughtworks.sameer.projectthoughtworks.utility.FragmentUtility;

import java.util.ArrayList;


/**
 * This fragment shows the Product list. It also has a method to add dummy data to database.
 */
public class ProductListFragment extends Fragment {

    private ProductListAdapter productListAdapter = null;
    private ListView productList = null;

    private ArrayList<ListItem> arrayListItems = null;

    public ProductListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        createDummyData();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);

        productListAdapter = new ProductListAdapter(this.getActivity(), arrayListItems);
        productList = (ListView) rootView.findViewById(R.id.product_list);
        productList.setAdapter(productListAdapter);

        productList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShopItem shopItem = (ShopItem)adapterView.getAdapter().getItem(i);
                String id = shopItem.getId();
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Bundle args = new Bundle();
                args.putString(ProductDetailsFragment.PRODUCT_ID, id);
                productDetailsFragment.setArguments(args);
                FragmentUtility.addFragmentOnStack(ProductListFragment.this.getActivity(), R.id.fragment_frame, productDetailsFragment, null);

            }
        });

        return rootView;
    }

    private void createDummyData() {
        SectionHeader firstSection = new SectionHeader();
        firstSection.setTitle("Electronics");
        SectionHeader secondSection = new SectionHeader();
        secondSection.setTitle("Furniture");

        ShopItem firstShopItem = new ShopItem();
        firstShopItem.setId("1");
        firstShopItem.setTitle("Microwave Oven");
        firstShopItem.setCategory("Electronics");
        firstShopItem.setPrice("7999.99");

        ShopItem secondShopItem = new ShopItem();
        secondShopItem.setId("2");
        secondShopItem.setTitle("Television");
        secondShopItem.setCategory("Electronics");
        secondShopItem.setPrice("17999.99");

        ShopItem thirdShopItem = new ShopItem();
        thirdShopItem.setId("3");
        thirdShopItem.setTitle("Vacuum Cleaner");
        thirdShopItem.setCategory("Electronics");
        thirdShopItem.setPrice("2999.99");

        ShopItem fourthShopItem = new ShopItem();
        fourthShopItem.setId("4");
        fourthShopItem.setTitle("Table");
        fourthShopItem.setCategory("Furniture");
        fourthShopItem.setPrice("4999.99");

        ShopItem fifthShopItem = new ShopItem();
        fifthShopItem.setId("5");
        fifthShopItem.setTitle("Chair");
        fifthShopItem.setCategory("Furniture");
        fifthShopItem.setPrice("999.99");

        ShopItem sixthShopItem = new ShopItem();
        sixthShopItem.setId("6");
        sixthShopItem.setTitle("Almirah");
        sixthShopItem.setCategory("Furniture");
        sixthShopItem.setPrice("5999.99");

        arrayListItems = new ArrayList<ListItem>();

        arrayListItems.add(firstSection);
        arrayListItems.add(firstShopItem);
        arrayListItems.add(secondShopItem);
        arrayListItems.add(thirdShopItem);
        arrayListItems.add(secondSection);
        arrayListItems.add(fourthShopItem);
        arrayListItems.add(fifthShopItem);
        arrayListItems.add(sixthShopItem);

        DatabaseHelper dbHelper = DatabaseHelper.getDatabaseInstance(this.getActivity().getApplicationContext());

        dbHelper.insertShopItem(secondShopItem);
        dbHelper.insertShopItem(firstShopItem);
        dbHelper.insertShopItem(thirdShopItem);
        dbHelper.insertShopItem(fourthShopItem);
        dbHelper.insertShopItem(fifthShopItem);
        dbHelper.insertShopItem(sixthShopItem);
    }


}
