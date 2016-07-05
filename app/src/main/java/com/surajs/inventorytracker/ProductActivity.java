package com.surajs.inventorytracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.surajs.inventorytracker.data.DBUtils;
import com.surajs.inventorytracker.data.InventoryDetailsContract;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

  private EditText inputProductName;
  private EditText inputProductQuantity;
  private EditText inputProductPrice;
  private EditText inputProductImage;
  private TextInputLayout inputLayoutProductName;
  private TextInputLayout inputLayoutProductQuantity;
  private TextInputLayout inputLayoutProductPrice;
  private Spinner supplierSpinner;

  private ArrayList<String> getSupplierList() {
    ArrayList<String> supplierList = new ArrayList<>();
    Cursor cursor =
        DBUtils.getInstance(this).readFromDB(InventoryDetailsContract.SupplierEntry.TABLE_NAME, null);
    if (cursor.moveToFirst()) {
      do {
        String supplierName = cursor.getString(
            cursor.getColumnIndex(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_NAME));
        supplierList.add(supplierName);
      } while (cursor.moveToNext());
    }
    return supplierList;
  }

  private long getSupplierId(String supplierName) {
    String selection = InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_NAME + " LIKE ?";
    String[] selectionArgs = { "%" + supplierName + "%" };
    Cursor cursor = DBUtils.getInstance(this)
        .readFromDB(InventoryDetailsContract.SupplierEntry.TABLE_NAME, null, selection, selectionArgs);
    cursor.moveToFirst();
    return cursor.getLong(
        cursor.getColumnIndex(InventoryDetailsContract.SupplierEntry.COLUMN_NAME_SUPPLIER_ID));
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    inputProductName = (EditText) findViewById(R.id.input_product_name);
    inputProductQuantity = (EditText) findViewById(R.id.input_product_quantity);
    inputProductPrice = (EditText) findViewById(R.id.input_product_price);
    inputProductImage = (EditText) findViewById(R.id.input_product_image);
    inputLayoutProductName = (TextInputLayout) findViewById(R.id.input_layout_product_name);
    inputLayoutProductQuantity = (TextInputLayout) findViewById(R.id.input_layout_product_quantity);
    inputLayoutProductPrice = (TextInputLayout) findViewById(R.id.input_layout_product_price);
    supplierSpinner = (Spinner) findViewById(R.id.product_suppliers);
    Button addProductBtn = (Button) findViewById(R.id.product_add);

    ArrayList<String> supplierList = getSupplierList();
    ArrayAdapter<String> spinnerAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supplierList);
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    supplierSpinner.setAdapter(spinnerAdapter);

    addProductBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        boolean formValid = true;
        String newProductName = inputProductName.getText().toString();
        int newProductQuantity = 0;
        try {
          newProductQuantity = Integer.parseInt(inputProductQuantity.getText().toString());
          inputLayoutProductQuantity.setErrorEnabled(false);
        } catch (NumberFormatException ex) {
          inputLayoutProductQuantity.setError(getString(R.string.err_msg_quantity));
          formValid = false;
        }
        Double newProductPrice = 0.0;
        try {
          newProductPrice = Double.parseDouble(inputProductPrice.getText().toString());
          inputLayoutProductPrice.setErrorEnabled(false);
        } catch (NumberFormatException ex) {
          inputLayoutProductPrice.setError(getString(R.string.err_msg_price));
          formValid = false;
        }
        String newProductImage = inputProductImage.getText().toString();

        if (newProductName.equals("")) {
          inputLayoutProductName.setError(getString(R.string.err_msg_name_empty));
          formValid = false;
        } else {
          inputLayoutProductName.setErrorEnabled(false);
        }

        String selectedSupplierName = supplierSpinner.getSelectedItem().toString();
        long supplierId = getSupplierId(selectedSupplierName);
        if (formValid) {
          ContentValues contentValues = new ContentValues();
          contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
              newProductName);
          contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY,
              newProductQuantity);
          contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE, newProductPrice);
          contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID, supplierId);
          contentValues.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL, newProductImage);
          DBUtils.getInstance(getBaseContext())
              .insertIntoDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, contentValues);
          MainActivity.refreshCursor();
          finish();
        }
      }
    });
  }
}
