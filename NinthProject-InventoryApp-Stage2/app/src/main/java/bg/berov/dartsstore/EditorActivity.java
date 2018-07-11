package bg.berov.dartsstore;

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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText modelName, price, quantity, weight, supplierName, supplierPhone;
    private Spinner sizeSpinner, materialSpinner;
    private int dartsSize = DartsEntry.SIZE_M, material = DartsEntry.MATERIAL_BRASS;
    public final static int EXISTING_DARTS_LOADER = 0;
    private Uri currentDartsUri;
    private boolean dartsHasChanged = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            dartsHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        modelName = (EditText) findViewById(R.id.editText_model_name);
        price = (EditText) findViewById(R.id.editText_price);
        quantity = (EditText) findViewById(R.id.editText_quantity);
        weight = (EditText) findViewById(R.id.editText_weight);
        supplierName = (EditText) findViewById(R.id.editText_supplier_name);
        supplierPhone = (EditText) findViewById(R.id.editText_supplier_phone);

        sizeSpinner = (Spinner) findViewById(R.id.spinner_size);
        materialSpinner = (Spinner) findViewById(R.id.spinner_material);

        modelName.setOnTouchListener(touchListener);
        price.setOnTouchListener(touchListener);
        quantity.setOnTouchListener(touchListener);
        weight.setOnTouchListener(touchListener);
        supplierName.setOnTouchListener(touchListener);
        supplierPhone.setOnTouchListener(touchListener);
        sizeSpinner.setOnTouchListener(touchListener);
        materialSpinner.setOnTouchListener(touchListener);

        Intent intent = getIntent();
        currentDartsUri = intent.getData();

        if (currentDartsUri == null) {
            setTitle("Add New Darts");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit Darts");
            getLoaderManager().initLoader(EXISTING_DARTS_LOADER, null, this);
        }

        setupSpinners();
    }


    private void setupSpinners() {
        ArrayAdapter sizeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_size_options, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter materialSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_material_options, R.layout.support_simple_spinner_dropdown_item);

        sizeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        materialSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        materialSpinner.setAdapter(materialSpinnerAdapter);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.size_L))) {
                        dartsSize = DartsEntry.SIZE_L;
                    } else if (selection.equals(getString(R.string.size_M))) {
                        dartsSize = DartsEntry.SIZE_M;
                    } else {
                        dartsSize = DartsEntry.SIZE_XL;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dartsSize = DartsEntry.SIZE_M; //????????????????
            }
        });

        materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.material_brass))) {
                        material = DartsEntry.MATERIAL_BRASS;
                    } else if (selection.equals(getString(R.string.material_nickel))) {
                        material = DartsEntry.MATERIAL_NICKEL;
                    } else {
                        material = DartsEntry.MATERIAL_TUNGSTEN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                material = DartsEntry.MATERIAL_TUNGSTEN;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_darts:
                if (saveDarts()) {
                    startActivity(new Intent(EditorActivity.this, MainActivity.class));
                    finish();
                }
                return true;
            case R.id.action_delete:
                showDartsDeleteConfirmation();
                return true;
            case android.R.id.home:
                if (!dartsHasChanged) {
                    super.onBackPressed();
                    startActivity(new Intent(EditorActivity.this, MainActivity.class));
                    finish();
                } else {
                    showUnsavedChangesDialog();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDartsDeleteConfirmation() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_delete_darts);

        builder.setPositiveButton(R.string.alert_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(EditorActivity.this, MainActivity.class));
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

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void deleteDarts() {
        if (currentDartsUri != null) {
            int rowsDeleted = getContentResolver().delete(currentDartsUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.toast_deletion_failed, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_deletion_successful, Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditorActivity.this, MainActivity.class));
                finish();
            }
        }
    }


    private boolean saveDarts() {
        String modelNameString = modelName.getText().toString().trim();
        String supplierNameString = supplierName.getText().toString().trim();
        String supplierPhoneString = supplierPhone.getText().toString().trim();

        int priceInteger;
        try {
            priceInteger = Integer.parseInt(price.getText().toString().trim());
        } catch (NumberFormatException e) {
            priceInteger = -1;
        }

        int quantityInteger;
        try {
            quantityInteger = Integer.parseInt(quantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            quantityInteger = -1;
        }

        int weightInteger;
        try {
            weightInteger = Integer.parseInt(weight.getText().toString().trim());
        } catch (NumberFormatException e) {
            weightInteger = -1;
        }

        if (currentDartsUri == null && TextUtils.isEmpty(modelNameString) && TextUtils.isEmpty(price.getText().toString().trim()) &&
                TextUtils.isEmpty(quantity.getText().toString().trim()) && TextUtils.isEmpty(weight.getText().toString().trim()) &&
                TextUtils.isEmpty(supplierNameString) && TextUtils.isEmpty(supplierPhoneString)) {
            Toast.makeText(this, R.string.toast_cant_save_empty_data, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidData(modelNameString, priceInteger, quantityInteger, weightInteger, supplierNameString, supplierPhoneString)) {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DartsEntry.COLUMN_DARTS_MODEL_NAME, modelNameString);
        contentValues.put(DartsEntry.COLUMN_DARTS_PRICE, priceInteger);
        contentValues.put(DartsEntry.COLUMN_DARTS_QUANTITY, quantityInteger);
        contentValues.put(DartsEntry.COLUMN_DARTS_WEIGHT, weightInteger);
        contentValues.put(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME, supplierNameString);
        contentValues.put(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE, supplierPhoneString);
        contentValues.put(DartsEntry.COLUMN_DARTS_SIZE, dartsSize);
        contentValues.put(DartsEntry.COLUMN_DARTS_MATERIAL, material);

        if (currentDartsUri == null) {
            Uri newUri = getContentResolver().insert(DartsEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                Toast.makeText(this, R.string.toast_insertion_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.toast_darts_saved, Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(currentDartsUri, contentValues, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, R.string.toast_insertion_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.toast_udate_successful, Toast.LENGTH_SHORT).show();
            }
        }

        return true;
    }


    private boolean isValidData(String modelNameString, int priceInteger, int quantityInteger, int weightInteger, String supplierNameString, String supplierPhoneString) {
        boolean isValid = true;
        String message = null;

        if (supplierPhoneString == null || supplierPhoneString.isEmpty()) {
            message = getString(R.string.check_supplier_phone);
            isValid = false;
            supplierPhone.requestFocus();
        }


        if (supplierNameString == null || supplierNameString.isEmpty()) {
            message = getString(R.string.check_supplier_name);
            isValid = false;
            supplierName.requestFocus();
        }

        if (weightInteger < 0) {
            message = getString(R.string.check_weight);
            isValid = false;
            weight.requestFocus();
        }

        if (quantityInteger < 0) {
            message = getString(R.string.check_quantity);
            isValid = false;
            quantity.requestFocus();
        }

        if (priceInteger < 0) {
            message = getString(R.string.check_price);
            isValid = false;
            price.requestFocus();
        }

        if (modelNameString == null || modelNameString.isEmpty()) {
            message = getString(R.string.check_model_name);
            isValid = false;
            modelName.requestFocus();
        }


        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        return isValid;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentDartsUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
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
            modelName.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MODEL_NAME)));
            price.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_PRICE)));
            weight.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_WEIGHT)));
            supplierName.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME)));
            quantity.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_QUANTITY)));
            supplierPhone.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE)));

            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MATERIAL)))) {
                case DartsEntry.MATERIAL_BRASS:
                    materialSpinner.setSelection(0);
                    break;
                case DartsEntry.MATERIAL_NICKEL:
                    materialSpinner.setSelection(1);
                    break;
                default:
                    materialSpinner.setSelection(2);
                    break;
            }

            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SIZE)))) {
                case DartsEntry.SIZE_M:
                    sizeSpinner.setSelection(0);
                    break;
                case DartsEntry.SIZE_L:
                    sizeSpinner.setSelection(1);
                    break;
                default:
                    sizeSpinner.setSelection(2);
                    break;
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        modelName.setText("");
        price.setText("");
        quantity.setText("");
        weight.setText("");
        supplierPhone.setText("");
        supplierName.setText("");
        sizeSpinner.setSelection(0);
        materialSpinner.setSelection(0);
    }


    @Override
    public void onBackPressed() {
        if (!dartsHasChanged) {
            super.onBackPressed();
            startActivity(new Intent(EditorActivity.this, MainActivity.class));
            finish();
        } else {
            showUnsavedChangesDialog();
        }
    }


    private void showUnsavedChangesDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_discard_changes);
        builder.setPositiveButton(R.string.alert_discard, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(EditorActivity.this, MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton(R.string.alert_dont_discard, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
