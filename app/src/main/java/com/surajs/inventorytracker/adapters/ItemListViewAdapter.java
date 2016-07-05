package com.surajs.inventorytracker.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.surajs.inventorytracker.DownloadImage;
import com.surajs.inventorytracker.MainActivity;
import com.surajs.inventorytracker.ItemDetailActivity;
import com.surajs.inventorytracker.R;
import com.surajs.inventorytracker.data.DBUtils;
import com.surajs.inventorytracker.data.InventoryDetailsContract;


public class ItemListViewAdapter extends CursorAdapter {


    private LayoutInflater cursorInflater;
    private int mSelectedPosition;
    private Context context;

    public ItemListViewAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.context = context;
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.product_list_item, parent, false);
    }

    @Override
    public void bindView(View v, final Context context, Cursor cursor) {
        LinearLayout productContainer = (LinearLayout) v.findViewById(R.id.listContainer);
        TextView productName = (TextView) v.findViewById(R.id.productName);
        TextView productQuantity = (TextView) v.findViewById(R.id.productQuantity);
        TextView productPrice = (TextView) v.findViewById(R.id.productPrice);
        ImageView productImage = (ImageView) v.findViewById(R.id.productImage);
        ImageButton saleBtn = (ImageButton) v.findViewById(R.id.saleButton);

        final int id = cursor.getInt(cursor.getColumnIndex(InventoryDetailsContract.ProductEntry._ID));
        final String title = cursor.getString(
                cursor.getColumnIndex(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        final int quantity =
                cursor.getInt(cursor.getColumnIndex(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY));
        final double price =
                cursor.getDouble(cursor.getColumnIndex(InventoryDetailsContract.ProductEntry.COLUMN_NAME_PRICE));
        final long supplierId = cursor.getLong(
                cursor.getColumnIndex(InventoryDetailsContract.ProductEntry.COLUMN_NAME_SUPPLIER_ID));
        final String imageUrl = cursor.getString(
                cursor.getColumnIndex(InventoryDetailsContract.ProductEntry.COLUMN_NAME_IMAGE_URL));

        productName.setText(title);
        productQuantity.setText("Quantity: " + quantity);
        productPrice.setText("INR: " + price);
        new DownloadImage(productImage).execute(imageUrl);

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = quantity - 1;
                ContentValues values = new ContentValues();
                values.put(InventoryDetailsContract.ProductEntry.COLUMN_NAME_QUANTITY, newQuantity);
                String selection = InventoryDetailsContract.ProductEntry._ID + " = ?";
                String[] selectionArgs = {String.valueOf(id)};
                DBUtils.getInstance(context)
                        .updateEntry(InventoryDetailsContract.ProductEntry.TABLE_NAME, values, selection,
                                selectionArgs);
                MainActivity.refreshCursor();
            }
        });

        productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Id", id);
                bundle.putString("Title", title);
                bundle.putInt("Quantity", quantity);
                bundle.putDouble("Price", price);
                bundle.putLong("SupplierId", supplierId);
                bundle.putString("ImageUrl", imageUrl);
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}