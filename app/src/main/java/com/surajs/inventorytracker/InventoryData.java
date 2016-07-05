package com.surajs.inventorytracker;

import android.app.Application;
import android.content.ContentValues;

import com.surajs.inventorytracker.data.DBUtils;
import com.surajs.inventorytracker.data.InventoryDetailsContract;



public class InventoryData extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createSupplierRecord();
        createProductRecord();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    private void createProductRecord() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, "Hershey's Chocolate Syrup");
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY, 208);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE, 99.00);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID, 1172);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL, "http://images.hersheysstore.com/imagesEdp/p113146z.jpg");
        DBUtils.getInstance(this)
                .insertIntoDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, "Bournville Dark Chocolate");
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY, 10);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE, 149.00);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID, 1169);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL, "http://listz.in/wp-content/uploads/2014/10/bournville-2.jpg");
        DBUtils.getInstance(this)
                .insertIntoDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, "Kellogs CornFlakes Orignal");
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY, 40);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE, 290.00);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID, 1172);
        contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL, "http://img1.21food.com/img/cj/2014/10/9/1412793116240251.jpg");
        DBUtils.getInstance(this)
                .insertIntoDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, contentValues);
    }

    private void createSupplierRecord() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_NAME,
                "Wallmart");
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_ID, 1172);
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_PHONE, 983462784);
        DBUtils.getInstance(this)
                .insertIntoDB(InventoryDetailsContract.SupplierEntry.TABLE_NAME, contentValues);
        contentValues = new ContentValues();
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_NAME, "CoolStuff Retail");
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_ID, 1169);
        contentValues.put(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_PHONE, 97654423);
        DBUtils.getInstance(this)
                .insertIntoDB(InventoryDetailsContract.SupplierEntry.TABLE_NAME, contentValues);
    }
}
