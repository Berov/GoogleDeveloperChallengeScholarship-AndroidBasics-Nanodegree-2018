package bg.berov.bulgariatourguide.model;

import java.util.ArrayList;

import bg.berov.bulgariatourguide.R;

public final class Util {


    private static ArrayList<TouristAttraction> touristAttractionArrayList = new ArrayList<TouristAttraction>();

    private static String category_museum = "Museum";
    private static String category_historical_place = "Historical Place";
    private static String category_restaurant = "Restaurant";

    private static String city_one = "Sofia";
    private static String city_two = "Burgas";
    private static String city_three = "Varna";
    private static String city_four = "Plovdiv";

    public static void addTouristObject(TouristAttraction item) {
        if (item != null) {
            touristAttractionArrayList.add(item);
        }
    }

    public static String getCity_one() {
        return city_one;
    }

    public static String getCity_two() {
        return city_two;
    }

    public static String getCity_three() {
        return city_three;
    }

    public static String getCity_four() {
        return city_four;
    }

    //??????????????????????? Dali
    public static ArrayList<TouristAttraction> getTouristAttractionArrayList(String city, String category) {
        ArrayList<TouristAttraction> list = new ArrayList();
        for (TouristAttraction touristAttraction : touristAttractionArrayList) {

            if (touristAttraction.getCity().equals(city) && touristAttraction.getCategory().equals(category)) {
                list.add(touristAttraction);
            }
        }

        return list;
        //TODO repare it for security Unmodifiable List or copy of the list
    }

    static {
        TouristAttraction a = new TouristAttraction("National Museum", R.drawable.test_image, "1 - Наципонален исторочески музей, бла бla", city_one, category_museum);
        TouristAttraction b = new TouristAttraction("Second Museum", R.drawable.test_image, "2 - B sdkjhfkjfh shdfk kjh kjfh kjh k", city_two, category_museum);
        TouristAttraction c = new TouristAttraction("Third Museum", R.drawable.test_image, "3 - C sjhf jfh fh fhfh rk wk  jrhf ", city_three, category_museum);
        TouristAttraction d = new TouristAttraction("Fourth Museum", R.drawable.test_image, "4 - D ijdc i ji i j ij", city_one, category_historical_place);
        TouristAttraction e = new TouristAttraction("Fifth Museum", R.drawable.test_image, "5 - E ikdj sk j lj ", city_two, category_historical_place);
        TouristAttraction f = new TouristAttraction("Sixth Museum", R.drawable.test_image, "6 - F soikjoi osf jo joj ", city_three, category_historical_place);
        TouristAttraction j = new TouristAttraction("National Museum", R.drawable.test_image, "7 - Наципонален исторочески музей, бла бla", city_one, category_restaurant);
        TouristAttraction h = new TouristAttraction("Second Museum", R.drawable.test_image, "8 - B sdkjhfkjfh shdfk kjh kjfh kjh k", city_two, category_restaurant);
        TouristAttraction k = new TouristAttraction("Third Museum", R.drawable.test_image, "9 - C sjhf jfh fh fhfh rk wk  jrhf ", city_three, category_restaurant);
        TouristAttraction l = new TouristAttraction("Fourth Museum", R.drawable.test_image, "10 - D ijdc i ji i j ij", city_one, category_museum);
        TouristAttraction m = new TouristAttraction("Fifth Museum", R.drawable.test_image, "11 - E ikdj sk j lj ", city_two, category_museum);
        TouristAttraction n = new TouristAttraction("Sixth Museum", R.drawable.test_image, "12 - F soikjoi osf jo joj ", city_three, category_museum);
        TouristAttraction o = new TouristAttraction("National Museum", R.drawable.test_image, "13 - Наципонален исторочески музей, бла бla", city_one, category_museum);
        TouristAttraction p = new TouristAttraction("Second Museum", R.drawable.test_image, "14 - B sdkjhfkjfh shdfk kjh kjfh kjh k", city_two, category_museum);
        TouristAttraction q = new TouristAttraction("Third Museum", R.drawable.test_image, "15 - C sjhf jfh fh fhfh rk wk  jrhf ", city_three, category_museum);
        TouristAttraction r = new TouristAttraction("Fourth Museum", R.drawable.test_image, "16 - D ijdc i ji i j ij", city_one, category_museum);
        TouristAttraction s = new TouristAttraction("Fifth Museum", R.drawable.test_image, "17 - E ikdj sk j lj ", city_two, category_museum);
        TouristAttraction t = new TouristAttraction("Sixth Museum", R.drawable.test_image, "18 - F soikjoi osf jo joj ", city_three, category_museum);
    }

}
