package com.thoughtworks.sameer.projectthoughtworks.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.thoughtworks.sameer.projectthoughtworks.R;
import com.thoughtworks.sameer.projectthoughtworks.utility.FragmentUtility;

/**
 * Main activity consisting of main Fragment frame.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_shop);
        setSupportActionBar(toolbar);

        ProductListFragment productListFragment = new ProductListFragment();
        FragmentUtility.replaceFragmentOnStack(this, R.id.fragment_frame, productListFragment, null);
    }
}
