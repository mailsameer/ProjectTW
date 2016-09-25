package com.thoughtworks.sameer.projectthoughtworks.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sameer on 9/25/2016.
 */

/**
 * Database helper class, all database related operations are done here.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static DatabaseHelper dataBaseHelper;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ProjectThoughtworks.db";

    //Table names in the database
    public static final String TABLE_CART = "SHOPPING_CART";
    public static final String TABLE_SHOP_ITEMS = "SHOPPING_ITEMS";

    //Columns in the table TABLE_CART
    public static final String KEY_CART_ID = "_id";
    public static final String KEY_PRODUCT_ID = "item_id";

    //Columns in the table TABLE_SHOP_ITEMS
    public static final String KEY_SHOPPING_ITEM_ID = "product_id";
    public static final String KEY_CATEGORY = "item_category";
    public static final String KEY_PRICE = "item_price";
    public static final String KEY_NAME = "item_name";

    //Query for creating TABLE_SHOP_ITEMS
    private String CREATE_SHOP_ITEMS_TABLE = "CREATE TABLE " + TABLE_SHOP_ITEMS + "("
            + KEY_SHOPPING_ITEM_ID + " TEXT," + KEY_CATEGORY + " TEXT,"
            + KEY_PRICE + " TEXT,"
            + KEY_NAME + " TEXT)";

    //Query for creating TABLE_CART
    private String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_ID + " TEXT)";


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    public static DatabaseHelper getDatabaseInstance(Context context) {
        if (dataBaseHelper == null) {
            return new DatabaseHelper(context);
        } else {
            return dataBaseHelper;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SHOP_ITEMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP_ITEMS);
        onCreate(sqLiteDatabase);
    }

    /**
     * Insert a product item in the products table.
     * @param shopItem
     */
    public void insertShopItem(ShopItem shopItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SHOPPING_ITEM_ID, shopItem.getId());
        values.put(KEY_CATEGORY, shopItem.getCategory());
        values.put(KEY_PRICE, shopItem.getPrice());
        values.put(KEY_NAME, shopItem.getTitle());

        sqLiteDatabase.insert(TABLE_SHOP_ITEMS, null, values);
        sqLiteDatabase.close();

    }

    /**
     * Insert a cart item in the cart table.
     * @param cartItem
     */
    public void insertItemToCart(CartItem cartItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PRODUCT_ID, cartItem.getId());

        sqLiteDatabase.insert(TABLE_CART, null, values);
        sqLiteDatabase.close();
    }

    /**
     * Delete a cart item from the shopping cart. Please note this does not delete the item from the Product Table.
     * @param cartId
     */
    public void deleteItemFromCart(int cartId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CART, "_id" + "=?", new String[] {String.valueOf(cartId)});
        sqLiteDatabase.close();
    }

    /**
     * Get All the items in the shopping cart.
     * @return
     */
    public ArrayList<CartItem> getItemsInCart() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0)
            return null;
        ArrayList<CartItem> itemsInCart = new ArrayList<CartItem>(cursor.getCount());
        int counter = 0;
        do {
            CartItem cartItem = new CartItem();
            String productId = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ID));
            cartItem.setCartId(cursor.getInt(cursor.getColumnIndex(KEY_CART_ID)));

            Cursor cursorOfShopItem = sqLiteDatabase.query(TABLE_SHOP_ITEMS, null, KEY_SHOPPING_ITEM_ID + "=?", new String[] {productId}, null, null, null);
            cursorOfShopItem.moveToFirst();

            cartItem.setId(cursorOfShopItem.getString(cursorOfShopItem.getColumnIndex(KEY_SHOPPING_ITEM_ID)));
            cartItem.setCategory(cursorOfShopItem.getString(cursorOfShopItem.getColumnIndex(KEY_CATEGORY)));
            cartItem.setTitle(cursorOfShopItem.getString(cursorOfShopItem.getColumnIndex(KEY_NAME)));
            cartItem.setPrice(cursorOfShopItem.getString(cursorOfShopItem.getColumnIndex(KEY_PRICE)));
            itemsInCart.add(cartItem);
            cursorOfShopItem.close();
            counter++;
        } while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return itemsInCart;
    }

    /**
     * Get the product using its Product ID.
     * @param id
     * @return
     */
    public ShopItem getItemBasedOnId(String id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_SHOP_ITEMS, null, KEY_SHOPPING_ITEM_ID + "=?", new String[] {id}, null, null, null);
        cursor.moveToFirst();

        ShopItem shopItem = new ShopItem();

        shopItem.setId(cursor.getString(cursor.getColumnIndex(KEY_SHOPPING_ITEM_ID)));
        shopItem.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
        shopItem.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        shopItem.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
        cursor.close();
        sqLiteDatabase.close();
        return shopItem;
    }
}
