package bg.berov.dartsstore;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import bg.berov.dartsstore.data.DartsContract.DartsEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private DartsCursorAdapter dartsCursorAdapter;
    public final static int DARTS_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView dartsItemsList = (ListView) findViewById(R.id.listView_items);
        View emptyView = findViewById(R.id.empty_view);

        dartsCursorAdapter = new DartsCursorAdapter(this, null);

        dartsItemsList.setEmptyView(emptyView);
        dartsItemsList.setAdapter(dartsCursorAdapter);

        dartsItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(DartsEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(DARTS_LOADER, null, MainActivity.this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                DartsEntry._ID,
                DartsEntry.COLUMN_DARTS_MODEL_NAME,
                DartsEntry.COLUMN_DARTS_QUANTITY,
                DartsEntry.COLUMN_DARTS_PRICE};

        return new CursorLoader(this,
                DartsEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        dartsCursorAdapter.swapCursor(cursor);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dartsCursorAdapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_darts:
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
                return true;
            case R.id.action_delete_all_darts_items:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_exit:
                showExitConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Exit?");
        builder.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_all_darts_deletion);
        builder.setPositiveButton(R.string.alert_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllDarts();
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


    private void deleteAllDarts() {
        int rowsDeleted = getContentResolver().delete(DartsEntry.CONTENT_URI, null, null);

        if (rowsDeleted == 0) {
            Toast.makeText(this, R.string.toast_deletion_failed, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.toast_all_darts_deleted, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }
}
