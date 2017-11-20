package com.student.portretov.lab5_database;

/**
 * Created by adminvp on 9/8/17.
 */

public class OrderContract {

    public OrderContract() {}

    public static abstract class OrderEntry {
        public static final String TABLE_NAME = "orders";

        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_LOGIN = "my_login";
        public static final String COLUMN_NAME_PASSWORD = "my_password";
        public static final String COLUMN_NAME_EMAIL = "my_email";
        public static final String COLUMN_NAME_TITLE = "my_title";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + OrderEntry.TABLE_NAME + " (" +
                        OrderEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        OrderEntry.COLUMN_NAME_LOGIN + " TEXT, " +
                        OrderEntry.COLUMN_NAME_PASSWORD + " TEXT, " +
                        OrderEntry.COLUMN_NAME_EMAIL + " TEXT, " +
                        OrderEntry.COLUMN_NAME_TITLE + " TEXT);";

        public static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXISTS " + OrderEntry.TABLE_NAME;
    }
}

