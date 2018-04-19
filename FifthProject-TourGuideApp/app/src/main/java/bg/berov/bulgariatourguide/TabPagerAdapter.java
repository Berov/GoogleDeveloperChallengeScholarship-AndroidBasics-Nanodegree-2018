package bg.berov.bulgariatourguide;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bg.berov.bulgariatourguide.model.Util;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabLabel = {"Museums", "Restaurants", "Historical Places"};


    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                return new SofiaFragment().setArguments(Util.getTouristAttractionArrayList(MainActivity.city, "Museum"));
//                return new CityFragment();
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.city, "Museum"));
            case 1:
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.city, "Historical Place"));
//                return new CityFragment();
            case 2:
                return SofiaFragment.newInstance(Util.getTouristAttractionArrayList(MainActivity.city, "Restaurant"));
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return tabLabel[position];
    }


    public void changeFragments() {
        //TODO

    }


}
