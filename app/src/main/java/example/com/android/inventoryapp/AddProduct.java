package example.com.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import example.com.android.inventoryapp.data.InventoryContract.ProductEntry;

public class AddProduct extends AppCompatActivity

{
    EditText etName, etPrice, etQuantity, etSupplier;
    Button btnImage;

    int quantity = 0, price = 0;
    int PICK_IMAGE_REQUEST = 1;
    byte[] img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        etName = (EditText) findViewById(R.id.etName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etSupplier = (EditText) findViewById(R.id.etSupplier);
        btnImage = (Button) findViewById(R.id.btnImage);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                img = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                saveProduct();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {

        if (etName.getText().length() == 0 || etSupplier.getText().length() == 0 ||
                etPrice.getText().length() == 0 || etQuantity.getText().length() == 0 || img == null)
        {
            Toast.makeText(this, getResources().getString(R.string.fill_all_details), Toast.LENGTH_SHORT).show();
            return;
        } else {
            String nameString = etName.getText().toString().trim();
            String supplierString = etSupplier.getText().toString().trim();
            quantity = Integer.parseInt(etQuantity.getText().toString().trim());
            price = Integer.parseInt(etPrice.getText().toString().trim());

            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_PRODUCT_NAME, nameString);
            values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, supplierString);
            values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
            values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
            values.put(ProductEntry.COLUMN_PRODUCT_IMAGE, img);

            getContentResolver().insert(ProductEntry.CONTENT_URI, values);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }

    }

}
