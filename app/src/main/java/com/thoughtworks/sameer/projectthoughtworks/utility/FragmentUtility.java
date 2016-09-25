package com.thoughtworks.sameer.projectthoughtworks.utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by sameer on 9/24/2016.
 */

/**
 * Fragment utility for adding/replacing fragments on display stack.
 */
public class FragmentUtility {

    public static void addFragmentOnStack(final FragmentActivity activity, final int containerId, final Fragment fragment, String tag) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(containerId, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    public static void replaceFragmentOnStack(final FragmentActivity activity, final int containerId, final Fragment fragment, String tag) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(containerId, fragment, tag);
            transaction.commit();
        }
    }


}
