package bg.berov.dartsstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;


public class DartsCursorAdapter extends CursorAdapter {

    public DartsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_darts, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView modelName = (TextView) view.findViewById(R.id.textView_darts_model);
        TextView modelPrice = (TextView) view.findViewById(R.id.textView_darts_price);
        TextView modelQuantity = (TextView) view.findViewById(R.id.textView_model_quantity);
        TextView modelId = (TextView) view.findViewById(R.id.textView_ID);
        Button sale = (Button) view.findViewById(R.id.button_sale);

        final int columnIndex = cursor.getInt(cursor.getColumnIndex(DartsEntry._ID));
        final int quantity = cursor.getInt(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_QUANTITY));

        modelName.setText(cursor.getString(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_MODEL_NAME)));
        modelPrice.setText(context.getString(R.string.adapter_price) + String.valueOf(cursor.getInt(cursor.getColumnIndex(DartsEntry.COLUMN_DARTS_PRICE))));
        modelQuantity.setText(context.getString(R.string.adapter_quantity) + String.valueOf(quantity));
        modelId.setText(Integer.toString(cursor.getPosition() + 1));

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(DartsEntry.CONTENT_URI, columnIndex);

                if (quantity == 0) {
                    Toast.makeText(context, R.string.adapter_out_of_stock, Toast.LENGTH_SHORT).show();
                } else {
                    int newQuantity = quantity - 1;

                    if (newQuantity == 0) {
                        Toast.makeText(context, R.string.adapter_out_of_stock, Toast.LENGTH_SHORT).show();
                    }

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DartsEntry.COLUMN_DARTS_QUANTITY, newQuantity);
                    context.getContentResolver().update(uri, contentValues, null, null);
                }
            }
        });
    }
}
