package bg.berov.bulgariatourguide;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bg.berov.bulgariatourguide.model.Util;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabLabel = {Util.CATEGORY_FIRST, Util.CATEGORY_SECOND, Util.CATEGORY_THIRD, Util.CATEGORY_FOURTH};
//Context context;


//    private String[] tabLabel =  context.getResource.getStringArray(R.array.categories_string_array);


    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.city, Util.CATEGORY_FIRST));
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.getTargetCity(), Util.CATEGORY_FIRST));
            case 1:
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.getTargetCity(), Util.CATEGORY_SECOND));
//                return new CityFragment();
            case 2:
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.getTargetCity(), Util.CATEGORY_THIRD));
            case 3:
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.getTargetCity(), Util.CATEGORY_FOURTH));
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return tabLabel[position];
    }


}
