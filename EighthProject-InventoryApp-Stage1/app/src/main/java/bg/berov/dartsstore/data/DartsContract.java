package bg.berov.dartsstore.data;

import android.provider.BaseColumns;

public class DartsContract {

    private DartsContract() {
    }


    public static final class DartsEntry implements BaseColumns {
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

        public static final int SIZE_M = 0;
        public static final int SIZE_L = 1;
        public static final int SIZE_XL = 2;

        public static final int MATERIAL_BRASS = 0;     //brass
        public static final int MATERIAL_NICKEL = 1;    //silver-nickel
        public static final int MATERIAL_TUNGSTEN = 2;  //tungsten alloy
    }
}
