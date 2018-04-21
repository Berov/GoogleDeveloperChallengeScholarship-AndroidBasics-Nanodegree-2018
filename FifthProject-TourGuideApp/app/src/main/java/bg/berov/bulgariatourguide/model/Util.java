package bg.berov.bulgariatourguide.model;

import java.util.ArrayList;

import bg.berov.bulgariatourguide.R;

public final class Util {


    private static ArrayList<TouristAttraction> touristAttractionArrayList = new ArrayList<TouristAttraction>();

    public static final String CATEGORY_FIRST = "Culture";          //Must to be same like res/strings/category_first!!!
    public static final String CATEGORY_SECOND = "Food @ Drink";    //Must to be same like res/strings/category_second!!!
    public static final String CATEGORY_THIRD = "History";          //Must to be same like res/strings/category_third!!!
    public static final String CATEGORY_FOURTH = "Attractions";     //Must to be same like res/strings/category_fourth!!!

    public static  final String CITY_ONE = "Sofia";     //Must to be same like res/strings/city_one!!!
    public static  final String CITY_TWO = "Varna";     //Must to be same like res/strings/city_two!!!
    public static  final String CITY_THREE = "Burgas";  //Must to be same like res/strings/city_three!!!
    public static  final String CITY_FOUR = "Plovdiv";  //Must to be same like res/strings/city_four!!!

//    public static String targetCity = CITY_ONE;

    public static void addTouristObject(TouristAttraction item) {
        if (item != null) {
            touristAttractionArrayList.add(item);
        }
    }


    //TODO repare it for security! Unmodifiable List or copy of the list
    public static ArrayList<TouristAttraction> getTouristAttractionArrayList(String city, String category) {

        ArrayList<TouristAttraction> list = new ArrayList<>();

        for (TouristAttraction touristAttraction : touristAttractionArrayList) {
            if (touristAttraction.getCity().equals(city) && touristAttraction.getCategory().equals(category)) {
                list.add(touristAttraction);
            }
        }
        return list;
    }

    //Add your content here
    static {
        TouristAttraction a = new TouristAttraction("National Museum", R.drawable.test_image, "1 - Наципонален исторочески музей, бла бla", CITY_ONE, CATEGORY_FIRST);
        TouristAttraction b = new TouristAttraction("Second Museum", R.drawable.test_image, "2 - B sdkjhfkjfh shdfk kjh kjfh kjh k", CITY_TWO, CATEGORY_FIRST);
        TouristAttraction c = new TouristAttraction("Third Museum", R.drawable.test_image, "3 - C sjhf jfh fh fhfh rk wk  jrhf ", CITY_THREE, CATEGORY_FIRST);
        TouristAttraction d = new TouristAttraction("Fourth Museum", R.drawable.test_image, "4 - D ijdc i ji i j ij", CITY_ONE, CATEGORY_SECOND);
        TouristAttraction e = new TouristAttraction("Fifth Museum", R.drawable.test_image, "5 - E ikdj sk j lj ", CITY_TWO, CATEGORY_SECOND);
        TouristAttraction f = new TouristAttraction("Sixth Museum", R.drawable.test_image, "6 - F soikjoi osf jo joj ", CITY_THREE, CATEGORY_SECOND);
        TouristAttraction j = new TouristAttraction("National Museum", R.drawable.test_image, "7 - Наципонален исторочески музей, бла бla", CITY_ONE, CATEGORY_THIRD);
        TouristAttraction h = new TouristAttraction("Second Museum", R.drawable.test_image, "8 - B sdkjhfkjfh shdfk kjh kjfh kjh k", CITY_TWO, CATEGORY_THIRD);
        TouristAttraction k = new TouristAttraction("Third Museum", R.drawable.test_image, "9 - C sjhf jfh fh fhfh rk wk  jrhf ", CITY_THREE, CATEGORY_THIRD);
        TouristAttraction l = new TouristAttraction("Fourth Museum", R.drawable.test_image, "10 - D ijdc i ji i j ij", CITY_ONE, CATEGORY_FIRST);
        TouristAttraction m = new TouristAttraction("Fifth Museum", R.drawable.test_image, "11 - E ikdj sk j lj ", CITY_TWO, CATEGORY_FIRST);
        TouristAttraction n = new TouristAttraction("Sixth Museum", R.drawable.test_image, "12 - F soikjoi osf jo joj ", CITY_THREE, CATEGORY_FIRST);
        TouristAttraction o = new TouristAttraction("National Museum", R.drawable.test_image, "13 - Наципонален исторочески музей, бла бla", CITY_ONE, CATEGORY_FIRST);
        TouristAttraction p = new TouristAttraction("Second Museum", R.drawable.test_image, "14 - B sdkjhfkjfh shdfk kjh kjfh kjh k", CITY_TWO, CATEGORY_FIRST);
        TouristAttraction q = new TouristAttraction("Third Museum", R.drawable.test_image, "15 - C sjhf jfh fh fhfh rk wk  jrhf ", CITY_THREE, CATEGORY_FIRST);
        TouristAttraction r = new TouristAttraction("Fourth Museum", R.drawable.test_image, "16 - D ijdc i ji i j ij", CITY_ONE, CATEGORY_FIRST);
        TouristAttraction s = new TouristAttraction("Fifth Museum", R.drawable.test_image, "17 - E ikdj sk j lj ", CITY_TWO, CATEGORY_FIRST);
        TouristAttraction t = new TouristAttraction("Sixth Museum", R.drawable.test_image, "18 - F soikjoi osf jo joj ", CITY_THREE, CATEGORY_FIRST);
    }

}
