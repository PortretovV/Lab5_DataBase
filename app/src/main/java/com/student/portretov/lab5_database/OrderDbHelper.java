package com.student.portretov.lab5_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminvp on 9/8/17.
 */

public class OrderDbHelper extends SQLiteOpenHelper implements OrderService<Long> {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SpecialOrder.db";
    private SQLiteDatabase db;

    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public OrderDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OrderContract.OrderEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(OrderContract.OrderEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public long createOrder(Order order) {
        // Gets the data repository in write mode
        db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME_LOGIN, order.getLogin());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_PASSWORD, order.getPassword());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_EMAIL, order.getEmail());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_TITLE, order.getTitle());

        long result = db.insert(OrderContract.OrderEntry.TABLE_NAME, null,values);
        db.close();
        return result;
    }

    private Order getOrderFromCursor(Cursor cursor){
        if(cursor != null && cursor.getCount() > 0){
            Long id = cursor.getLong(cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME_ID));
            String login = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME_LOGIN));
            String password = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME_EMAIL));
            String title = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME_TITLE));

            return new Order(id, login, password, email, title);
        }
        return null;
    }

    @Override
    public List<Order> findOrderList() {
        List<Order> orderList = new ArrayList<>();
        db = this.getReadableDatabase();

        String[] projection = {
                OrderContract.OrderEntry.COLUMN_NAME_ID,
                OrderContract.OrderEntry.COLUMN_NAME_LOGIN,
                OrderContract.OrderEntry.COLUMN_NAME_PASSWORD,
                OrderContract.OrderEntry.COLUMN_NAME_EMAIL,
                OrderContract.OrderEntry.COLUMN_NAME_TITLE
        };

//         How you want the results sorted in the resulting Cursor
//        String sortOrder = OrderContract.OrderEntry.COLUMN_NAME_LOGIN + " ASC";

//        Cursor cursor = db.rawQuery("SELECT * FROM "+ OrderContract.OrderEntry.TABLE_NAME, null);

        Cursor cursor = db.query(
                OrderContract.OrderEntry.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.moveToFirst()){
            do {
                orderList.add(getOrderFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return orderList;
    }

    @Override
    public int deleteOrder(Long orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = OrderContract.OrderEntry.COLUMN_NAME_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(orderId) };
        // Issue SQL statement.
        int result = db.delete(OrderContract.OrderEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
        return result;
    }

    @Override
    public int updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME_LOGIN, order.getLogin());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_PASSWORD, order.getPassword());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_EMAIL, order.getEmail());
        values.put(OrderContract.OrderEntry.COLUMN_NAME_TITLE, order.getTitle());

        // Which row to update, based on the ID
        String selection = OrderContract.OrderEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(order.getId()) };

        int result = db.update(
                OrderContract.OrderEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return result;
    }
}
