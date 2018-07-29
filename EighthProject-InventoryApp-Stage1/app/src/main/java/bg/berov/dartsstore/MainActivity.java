package bg.berov.dartsstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;
import bg.berov.dartsstore.data.DartsDbHelper;

public class MainActivity extends AppCompatActivity {

    private DartsDbHelper dartsDbHelper;
    private String dartsModelName, suplierName, suplierPhoneNumber;
    private int price, quantity, size, weight, material;
    private Button buttonRead, buttonInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInsert = (Button) findViewById(R.id.button_add_darts);
        buttonRead = (Button) findViewById(R.id.button_read_table);

        //test values
        dartsModelName = "Stratos";
        suplierName = "Gogo Ltd.";
        suplierPhoneNumber = "+359 123 4567";
        price = 55;
        quantity = 12;
        size = DartsEntry.SIZE_L;
        weight = 23;
        material = DartsEntry.MATERIAL_TUNGSTEN;

        dartsDbHelper = new DartsDbHelper(this);


        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }


    private void insertData() {
        SQLiteDatabase db = dartsDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DartsEntry.COLUMN_DARTS_MODEL_NAME, dartsModelName);
        values.put(DartsEntry.COLUMN_DARTS_PRICE, price);
        values.put(DartsEntry.COLUMN_DARTS_QUANTITY, quantity);
        values.put(DartsEntry.COLUMN_DARTS_SIZE, size);
        values.put(DartsEntry.COLUMN_DARTS_WEIGHT, weight);
        values.put(DartsEntry.COLUMN_DARTS_MATERIAL, material);
        values.put(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME, suplierName);
        values.put(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE, suplierPhoneNumber);

        long newRowId = db.insert(DartsEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving values", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Darts saved with row id: " + newRowId, Toast.LENGTH_LONG).show();
        }
    }


    private void queryData() {

        SQLiteDatabase db = dartsDbHelper.getReadableDatabase();

        String[] projection = {
                DartsEntry._ID,
                DartsEntry.COLUMN_DARTS_MODEL_NAME,
                DartsEntry.COLUMN_DARTS_SIZE,
                DartsEntry.COLUMN_DARTS_QUANTITY,
                DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE};

        Cursor cursor = db.query(
                DartsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        StringBuilder textToDisplay = new StringBuilder();
        try {
            textToDisplay.append("The darts table contains " + cursor.getCount() + " items.\n\n");
            textToDisplay.append(DartsEntry._ID + " - " +
                    DartsEntry.COLUMN_DARTS_MODEL_NAME + " - " +
                    DartsEntry.COLUMN_DARTS_SIZE + " - " +
                    DartsEntry.COLUMN_DARTS_QUANTITY + " - " +
                    DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE + "\n");


            int idColumnIndex = cursor.getColumnIndex(DartsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MODEL_NAME);
            int sizeColumnIndex = cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SIZE);
            int quantityColumnIndex = cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_QUANTITY);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE);


            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSize = null;
                switch (cursor.getInt(sizeColumnIndex)) {
                    case 0:
                        currentSize = "M";
                        break;
                    case 1:
                        currentSize = "L";
                        break;
                    case 2:
                        currentSize = "XL";
                        break;
                }
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSuplierPhone = cursor.getString(supplierPhoneColumnIndex);

                textToDisplay.append("\n" + currentID + " - " +
                        currentName + " - " +
                        currentSize + " - " +
                        currentQuantity + " - " +
                        currentSuplierPhone);
            }
        } finally {
            cursor.close();
        }

        Toast.makeText(this, textToDisplay.toString(), Toast.LENGTH_LONG).show();
    }
}
