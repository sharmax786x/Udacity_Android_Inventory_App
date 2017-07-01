package example.com.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import example.com.android.inventoryapp.data.InventoryContract.ProductEntry;

public class ProductCursorAdapter extends CursorAdapter {
    int z = 0;

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        final TextView tvQuantity = (TextView) view.findViewById(R.id.tvQty);

        tvName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME)));
        tvPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE)));
        tvQuantity.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY)));
        TextView saleBtn = (TextView) view.findViewById(R.id.btnSale);
        final TextView hidden = (TextView) view.findViewById(R.id.tvhidden);
        hidden.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry._ID)));


        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(hidden.getText().toString().trim());

                if (Integer.parseInt(tvQuantity.getText().toString().trim()) > 0) {
                    Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, i);
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, Integer.parseInt(tvQuantity.getText().toString().trim()) - 1);
                    context.getContentResolver().update(currentProductUri, values, null, null);
                }
            }
        });

    }

}
