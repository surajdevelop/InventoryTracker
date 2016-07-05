package com.surajs.inventorytracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by prashant on 6/26/16.
 */
public class DBUtils {

  private InventoryDbHelper mDbHelper;
  private static DBUtils _INSTANCE;
  private final String LOG_TAG = DBUtils.class.getSimpleName();

  private DBUtils(Context context) {
    mDbHelper = new InventoryDbHelper(context);
  }

  public static DBUtils getInstance(Context context) {
    if (context == null) return null;
    if (_INSTANCE == null) {
      _INSTANCE = new DBUtils(context);
    }
    return _INSTANCE;
  }

  public void insertIntoDB(String tableName, ContentValues contentValues) {
    if (tableName != null && contentValues != null) {
      SQLiteDatabase db = mDbHelper.getWritableDatabase();
      db.insert(tableName, null, contentValues);
    } else {
      Log.e(LOG_TAG, "Error inserting into db");
    }
  }

  public Cursor readFromDB(String tableName, String[] projection) {
    if (tableName != null) {
      SQLiteDatabase db = mDbHelper.getReadableDatabase();
      return db.query(tableName, projection, null, null, null, null, null);
    } else {
      Log.e(LOG_TAG, "Error reading from table");
    }
    return null;
  }

  public Cursor readFromDB(String tableName, String[] projection, String selection,
      String[] selectionArgs) {
    if (tableName != null) {
      SQLiteDatabase db = mDbHelper.getReadableDatabase();
      return db.query(tableName, projection, selection, selectionArgs, null, null, null);
    } else {
      Log.e(LOG_TAG, "Error reading from table");
    }
    return null;
  }

  public void deleteEntries(String tableName, String selection, String[] selectionArgs) {
    if (tableName != null) {
      SQLiteDatabase db = mDbHelper.getReadableDatabase();
      db.delete(tableName, selection, selectionArgs);
    } else {
      Log.e(LOG_TAG, "Error deleting table");
    }
  }

  public void updateEntry(String tableName, ContentValues contentValues, String selection,
      String[] selectionArgs) {
    if (tableName != null) {
      SQLiteDatabase db = mDbHelper.getReadableDatabase();
      db.update(tableName, contentValues, selection, selectionArgs);
    } else {
      Log.e(LOG_TAG, "Error updating records");
    }
  }
}
