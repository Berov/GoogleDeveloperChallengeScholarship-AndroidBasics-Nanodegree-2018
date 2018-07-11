package bg.berov.dartsstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;

public class DartsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "darts.db";
    private static final int DATABASE_VERSION = 1;


    public DartsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String DARTS_DATABASE_CREATE = "CREATE TABLE " + DartsEntry.TABLE_NAME + "(" +
                DartsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DartsEntry.COLUMN_DARTS_MODEL_NAME + " TEXT NOT NULL ," +
                DartsEntry.COLUMN_DARTS_PRICE + " INTEGER NOT NULL," +
                DartsEntry.COLUMN_DARTS_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                DartsEntry.COLUMN_DARTS_SIZE + " INTEGER NOT NULL, " +
                DartsEntry.COLUMN_DARTS_WEIGHT + " INTEGER NOT NULL, " +
                DartsEntry.COLUMN_DARTS_MATERIAL + " INTEGER NOT NULL, " +
                DartsEntry.COLUMN_DARTS_SUPPLIER_NAME + " TEXT NOT NULL ," +
                DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE + " TEXT NOT NULL );";

        sqLiteDatabase.execSQL(DARTS_DATABASE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //do nothing for now
    }
}
