package com.surajs.inventorytracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prashant on 6/26/16.
 */
public class InventoryDbHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  static final String DATABASE_NAME = "inventory.db";

  private static final String CREATE_INVENTORY_PRODUCT_TABLE =
      "CREATE TABLE " + InventoryDetailsContract.ProductEntry.TABLE_NAME + " (" +
          InventoryDetailsContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + " TEXT NOT NULL," +
          InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY + " INT NOT NULL," +
          InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE + " DOUBLE NOT NULL," +
          InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID + " LONG NOT NULL," +
          InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL + " TEXT NOT NULL);";

  private static final String DELETE_INVENTORY_PRODUCT_TABLE =
      "DROP TABLE IF EXISTS" + InventoryDetailsContract.ProductEntry.TABLE_NAME;

  private static final String CREATE_INVENTORY_SUPPLIER_TABLE =
      "CREATE TABLE " + InventoryDetailsContract.SupplierEntry.TABLE_NAME + " (" +
          InventoryDetailsContract.SupplierEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_NAME + " TEXT NOT NULL," +
          InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_ID + " INT NOT NULL," +
          InventoryDetailsContract.SupplierEntry.COLUMN_NAME_PHONE + " INT NOT NULL);";

  private static final String DELETE_INVENTORY_SUPPLIER_TABLE =
      "DROP TABLE IF EXISTS" + InventoryDetailsContract.SupplierEntry.TABLE_NAME;

  private Context context;

  public InventoryDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_INVENTORY_SUPPLIER_TABLE);
    sqLiteDatabase.execSQL(CREATE_INVENTORY_PRODUCT_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL(DELETE_INVENTORY_SUPPLIER_TABLE);
    sqLiteDatabase.execSQL(DELETE_INVENTORY_PRODUCT_TABLE);
    onCreate(sqLiteDatabase);
  }

  public void deleteDatabase() {
    context.deleteDatabase(DATABASE_NAME);
  }
}


