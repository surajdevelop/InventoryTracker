package com.surajs.inventorytracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.surajs.inventorytracker.adapters.ItemListViewAdapter;
import com.surajs.inventorytracker.data.DBUtils;
import com.surajs.inventorytracker.data.InventoryDetailsContract;

public class MainActivity extends AppCompatActivity {

  public static ItemListViewAdapter customAdapter;
  Cursor mCursor;
  private ListView listView;
  private static Context mContext;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mContext = this;
    TextView infoText = (TextView) findViewById(R.id.product_empty_view);
    listView = (ListView) findViewById(R.id.productList);
    listView.setEmptyView(infoText);
    mCursor = DBUtils.getInstance(this).readFromDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, null);
    if (mCursor != null) {
      new Handler().post(new Runnable() {
        @Override public void run() {
          customAdapter = new ItemListViewAdapter(MainActivity.this, mCursor, 0);
          listView.setAdapter(customAdapter);
        }
      });
    } else {
      infoText.setVisibility(View.VISIBLE);
    }
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent addProductIntent = new Intent(getApplicationContext(), ProductActivity.class);
        startActivity(addProductIntent);
      }
    });
  }

  public static void refreshCursor() {
    Cursor cursor =
        DBUtils.getInstance(mContext).readFromDB(InventoryDetailsContract.ProductEntry.TABLE_NAME, null);
    customAdapter.swapCursor(cursor);
  }
}
