package bg.berov.dartsstore.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DartsContract {

    private DartsContract() {
    }

    public static final String CONTENT_AUTHORITY = "bg.berov.dartsstore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DARTS = "darts";


    public static final class DartsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DARTS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DARTS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DARTS;


        public final static String TABLE_NAME = "darts";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DARTS_MODEL_NAME = "name";
        public final static String COLUMN_DARTS_PRICE = "price";
        public final static String COLUMN_DARTS_QUANTITY = "quantity";
        public final static String COLUMN_DARTS_SIZE = "size";
        public final static String COLUMN_DARTS_WEIGHT = "weight";
        public final static String COLUMN_DARTS_MATERIAL = "material";

        public final static String COLUMN_DARTS_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_DARTS_SUPPLIER_PHONE = "supplier_phone";

        public static final int SIZE_M = 0;     //barrel 40mm
        public static final int SIZE_L = 1;     //barrel 45mm
        public static final int SIZE_XL = 2;    //barrel 50mm

        public static final int MATERIAL_BRASS = 0;     //brass
        public static final int MATERIAL_NICKEL = 1;    //silver-nickel
        public static final int MATERIAL_TUNGSTEN = 2;  //tungsten alloy


        public static boolean isValidSize(int size) {
            return (size == SIZE_M || size == SIZE_L || size == SIZE_XL);
        }

        public static boolean isValidMaterial(int material) {
            return (material == MATERIAL_BRASS || material == MATERIAL_NICKEL || material == MATERIAL_TUNGSTEN);
        }
    }
}
