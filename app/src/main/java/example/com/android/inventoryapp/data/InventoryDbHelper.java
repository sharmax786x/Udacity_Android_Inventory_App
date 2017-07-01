package example.com.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.com.android.inventoryapp.data.InventoryContract.ProductEntry;

public class InventoryDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "inventory.db";
    public static int DATABASE_VERSION = 1;
    public static String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductEntry.table_name + "(" + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT," + ProductEntry.COLUMN_PRODUCT_PRICE +
            " INTEGER, " + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER, " + ProductEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT ," + ProductEntry.COLUMN_PRODUCT_IMAGE + " BLOB)";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exsts " + ProductEntry.table_name);
        onCreate(db);
    }
}
