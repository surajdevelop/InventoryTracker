package com.surajs.inventorytracker.data;

import android.provider.BaseColumns;

/**
 * Created by prashant on 6/26/16.
 */
public final class InventoryDetailsContract {
  private InventoryDetailsContract() {
  }

  public static abstract class ProductEntry implements BaseColumns {
    public static final String TABLE_NAME = "productEntry";
    public static final String COLUMN_NAME_PRODUCT_NAME = "name";
    public static final String COLUMN_NAME_QUANTITY = "quantity";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_SUPPLIER_ID = "supplierId";
    public static final String COLUMN_NAME_IMAGE_URL = "imageUrl";
  }

  public static abstract class SupplierEntry implements BaseColumns {
    public static final String TABLE_NAME = "supplierEntry";
    public static final String COLUMN_NAME_SUPPLIER_NAME = "name";
    public static final String COLUMN_NAME_SUPPLIER_ID = "id";
    public static final String COLUMN_NAME_PHONE = "phone";
  }
}
