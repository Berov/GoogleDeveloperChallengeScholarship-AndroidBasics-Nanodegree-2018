package bg.berov.dartsstore.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;


public class DartsProvider extends ContentProvider {
    private static final int DARTS = 1;
    private static final int DARTS_ID = 2;
    private static final UriMatcher dartsUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DartsDbHelper dartsDbHelper;

    static {
        dartsUriMatcher.addURI(DartsContract.CONTENT_AUTHORITY, DartsContract.PATH_DARTS, DARTS);
        dartsUriMatcher.addURI(DartsContract.CONTENT_AUTHORITY, DartsContract.PATH_DARTS + "/#", DARTS_ID);
    }


    @Override
    public boolean onCreate() {
        dartsDbHelper = new DartsDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dartsDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = dartsUriMatcher.match(uri);

        switch (match) {
            case DARTS:
                cursor = db.query(DartsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DARTS_ID:
                selection = DartsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))}; //use selectionArgs for a row query NB
                cursor = db.query(DartsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("QUERY - unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        final int match = dartsUriMatcher.match(uri);

        switch (match) {
            case DARTS:
                return DartsEntry.CONTENT_LIST_TYPE;
            case DARTS_ID:
                return DartsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = dartsUriMatcher.match(uri);

        switch (match) {
            case DARTS:
                return insertDarts(uri, contentValues);
            default:
                throw new IllegalArgumentException("INSERT - Failed to insert row for " + uri);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dartsDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = dartsUriMatcher.match(uri);

        switch (match) {
            case DARTS:
                rowsDeleted = database.delete(DartsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DARTS_ID:
                selection = DartsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(DartsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong deletion for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = dartsUriMatcher.match(uri);

        switch (match) {
            case DARTS:
                return updateDarts(uri, contentValues, selection, selectionArgs);
            case DARTS_ID:
                selection = DartsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateDarts(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("UPDATE - wrong update to " + uri);
        }
    }


    private int updateDarts(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        if (contentValues.size() == 0) {
            return 0;
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_MODEL_NAME)) {
            String name = contentValues.getAsString(DartsEntry.COLUMN_DARTS_MODEL_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Model name required!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_PRICE)) {
            Integer price = contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_PRICE);
            if (price == null || price < 0) {
                throw new IllegalArgumentException("Price required!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_QUANTITY)) {
            Integer quantity = contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_QUANTITY);
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("Negative quantity is not possible!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_SIZE)) {
            Integer size = contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_SIZE);
            if (size == null || !DartsEntry.isValidSize(size)) {
                throw new IllegalArgumentException("Wrong size!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_WEIGHT)) {
            Integer weight = contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_WEIGHT);
            if (weight == null || weight < 0) {
                throw new IllegalArgumentException("Wrong weight!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_MATERIAL)) {
            Integer material = contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_MATERIAL);
            if (material == null || !DartsEntry.isValidMaterial(material)) {
                throw new IllegalArgumentException("Unknown material!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME)) {
            String supplierName = contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME);
            if (supplierName == null || supplierName.isEmpty()) {
                throw new IllegalArgumentException("Strange supplier's name!");
            }
        }

        if (contentValues.containsKey(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE)) {
            String supplierPhone = contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE);
            if (supplierPhone == null || supplierPhone.isEmpty()) {
                throw new IllegalArgumentException("Supplier without a phone is useless!");
            }
        }

        SQLiteDatabase database = dartsDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(DartsEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }


    private Uri insertDarts(Uri uri, ContentValues contentValues) {

        if (!isValidContentValues(contentValues)) {
            return null;
        }

        SQLiteDatabase db = dartsDbHelper.getWritableDatabase();
        long newRowId = db.insert(DartsEntry.TABLE_NAME, null, contentValues);

        if (newRowId == -1) {
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, newRowId);
    }


    private boolean isValidContentValues(ContentValues contentValues) {
        boolean flag = true;

        if (contentValues.getAsString(DartsEntry.COLUMN_DARTS_MODEL_NAME) == null || contentValues.getAsString(DartsEntry.COLUMN_DARTS_MODEL_NAME).isEmpty()) {
            flag = false;
            throw new IllegalArgumentException("Model name required!");
        }

        if (contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_PRICE) <= 0 || contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_PRICE) == null) {
            flag = false;
            throw new IllegalArgumentException("Price required!");
        }

        if (contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_QUANTITY) < 0) {
            flag = false;
            throw new IllegalArgumentException("Negative quantity is not possible!");
        }

        if (!DartsEntry.isValidSize(contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_SIZE)) || contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_SIZE) == null) {
            flag = false;
            throw new IllegalArgumentException("Wrong size!");
        }

        if (contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_WEIGHT) < 0 || contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_WEIGHT) == null) {
            flag = false;
            throw new IllegalArgumentException("Wrong weight!");
        }

        if (!DartsEntry.isValidMaterial(contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_MATERIAL)) || contentValues.getAsInteger(DartsEntry.COLUMN_DARTS_MATERIAL) == null) {
            flag = false;
            throw new IllegalArgumentException("Unknown material!");
        }

        if (contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME).isEmpty() || contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_NAME) == null) {
            flag = false;
            throw new IllegalArgumentException("Strange supplier's name!");
        }

        if (contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE).isEmpty() || contentValues.getAsString(DartsEntry.COLUMN_DARTS_SUPPLIER_PHONE) == null) {
            flag = false;
            throw new IllegalArgumentException("Supplier without a phone is useless!");
        }

        return flag;
    }
}
