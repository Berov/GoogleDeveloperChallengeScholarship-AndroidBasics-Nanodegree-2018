package bg.berov.dartsstore;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;

public class ItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView model, price, quantity, size, weight, material, suplier, suplierPhone;
    private Button increase, decrease, call;
    public static final int EXISTING_DARTS_LOADER = 0;
    private Uri currentDartsUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        model = (TextView) findViewById(R.id.textView_item_model_name);
        price = (TextView) findViewById(R.id.textView_item_price);
        quantity = (TextView) findViewById(R.id.textView_item_quantity);
        size = (TextView) findViewById(R.id.textView_item_size);
        weight = (TextView) findViewById(R.id.textView_item_weight);
        material = (TextView) findViewById(R.id.textView_item_material);
        suplier = (TextView) findViewById(R.id.textView_item_supplier);
        suplierPhone = (TextView) findViewById(R.id.textView_item_supplier_phone);
        call = (Button) findViewById(R.id.button_item_call_supplier);
        increase = (Button) findViewById(R.id.button_item_increase);
        decrease = (Button) findViewById(R.id.button_item_decrease);

        Intent intent = getIntent();
        currentDartsUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_DARTS_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                DartsEntry._ID,
                DartsEntry.COLUMN_DARTS_PRICE,
                DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE,
                DartsEntry.COLUMN_DARTS_QUANTITY,
                DartsEntry.COLUMN_DARTS_SIZE,
                DartsEntry.COLUMN_DARTS_MODEL_NAME,
                DartsEntry.COLUMN_DARTS_MATERIAL,
                DartsEntry.COLUMN_DARTS_WEIGHT,
                DartsEntry.COLUMN_DARTS_SUPPLIER_NAME};

        return new CursorLoader(this,
                currentDartsUri,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            model.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MODEL_NAME)));
            setTitle("Model: " + cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MODEL_NAME)));
            price.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_PRICE)));
            weight.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_WEIGHT)));
            suplier.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME)));
            quantity.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_QUANTITY)));

            final String phone = cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE));
            suplierPhone.setText(phone);

            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MATERIAL)))) {
                case DartsEntry.MATERIAL_BRASS:
                    material.setText(getResources().getString(R.string.material_brass));
                    break;
                case DartsEntry.MATERIAL_NICKEL:
                    material.setText(getResources().getString(R.string.material_nickel));
                    break;
                case DartsEntry.MATERIAL_TUNGSTEN:
                    material.setText(getResources().getString(R.string.material_tungsten));
                    break;
                default:
                    material.setText(R.string.unknown_material);
            }

            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SIZE)))) {
                case DartsEntry.SIZE_M:
                    size.setText(getResources().getString(R.string.size_M));
                    break;
                case DartsEntry.SIZE_L:
                    size.setText(getResources().getString(R.string.size_L));
                    break;
                case DartsEntry.SIZE_XL:
                    size.setText(getResources().getString(R.string.size_XL));
                    break;
                default:
                    size.setText(R.string.unknown_size);
            }

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            });

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DartsEntry.COLUMN_DARTS_QUANTITY, Integer.parseInt(quantity.getText().toString().trim()) + 1);
                    getContentResolver().update(currentDartsUri, contentValues, null, null);
                }
            });

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Integer.parseInt(quantity.getText().toString().trim()) <= 0) {
                        Toast.makeText(ItemActivity.this, R.string.toast_out_of_stock, Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DartsEntry.COLUMN_DARTS_QUANTITY, Integer.parseInt(quantity.getText().toString().trim()) - 1);
                        getContentResolver().update(currentDartsUri, contentValues, null, null);
                    }
                }
            });
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //nothing to do
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item_edit_darts:
                Intent intent = new Intent(ItemActivity.this, EditorActivity.class);
                intent.setData(currentDartsUri);
                currentDartsUri = null;
                startActivity(intent);
                return true;
            case R.id.action_item_delete_darts:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_confirm_darts_deletion);

        builder.setPositiveButton(R.string.alert_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteDarts();
            }
        });

        builder.setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void deleteDarts() {
        if (currentDartsUri != null) {
            int rowsDeleted = getContentResolver().delete(currentDartsUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.toast_deletion_failed, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_deletion_successful, Toast.LENGTH_LONG).show();
            }
        }

        finish();
    }
}
