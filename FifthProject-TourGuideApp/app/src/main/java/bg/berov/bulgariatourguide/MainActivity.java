package bg.berov.bulgariatourguide;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

import bg.berov.bulgariatourguide.model.TouristAttraction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CategoryTabsPager tabsPager;

    private static ArrayList<TouristAttraction> touristAttractionArrayList;
    private ImageView headerImage;

    private String categoryFirst;
    private String categorySecond;
    private String categoryThird;
    private String categoryFourth;

    private String cityOne;
    private String cityTwo;
    private String cityThree;
    private String cityFour;

    private String activeCity;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touristAttractionArrayList = new ArrayList<TouristAttraction>();
        //add content--------------
        new TouristAttraction(getString(R.string.national_theater), R.drawable.national_theater, getString(R.string.national_theater_description), getString(R.string.city_one), getString(R.string.category_first));
        new TouristAttraction(getString(R.string.sofia_theater), R.drawable.sofia_theater, getString(R.string.sofia_theater_description), getString(R.string.city_one), getString(R.string.category_first));
        new TouristAttraction(getString(R.string.sofia_opera), R.drawable.sofia_opera, getString(R.string.sofia_opera_description), getString(R.string.city_one), getString(R.string.category_first));
        new TouristAttraction(getString(R.string.sofia_ndk), R.drawable.sofia_ndk, getString(R.string.sofia_ndk_description), getString(R.string.city_one), getString(R.string.category_first));
        new TouristAttraction(getString(R.string.sofia_street_art_tour), R.drawable.sofia_streetart, getString(R.string.sofia_street_art_tour_description), getString(R.string.city_one), getString(R.string.category_fourth));
        new TouristAttraction(getString(R.string.sofia_live_club), R.drawable.sofia_live_club, getString(R.string.sofia_live_club_description), getString(R.string.city_one), getString(R.string.category_fourth));
        new TouristAttraction(getString(R.string.sofia_zoo), R.drawable.sofia_zoo, getString(R.string.sofia_zoo_description), getString(R.string.city_one), getString(R.string.category_fourth));
        new TouristAttraction(getString(R.string.sofia_al_nevski), R.drawable.sofia_al_nevski, getString(R.string.sofia_al_nevski_description), getString(R.string.city_one), getString(R.string.category_third));
        new TouristAttraction(getString(R.string.sofia_nhm), R.drawable.sofia_nhm, getString(R.string.sofia_nhm_description), getString(R.string.city_one), getString(R.string.category_third));
        new TouristAttraction(getString(R.string.sofia_rotunda), R.drawable.sofia_rotunda, getString(R.string.sofia_rotunda_description), getString(R.string.city_one), getString(R.string.category_third));
        new TouristAttraction(getString(R.string.sofia_east_gate), R.drawable.sofia_east_gate, getString(R.string.sofia_east_gate_description), getString(R.string.city_one), getString(R.string.category_third));
        new TouristAttraction(getString(R.string.sofia_roman_wall), R.drawable.sofia_roman_wall, getString(R.string.sofia_roman_wall_description), getString(R.string.city_one), getString(R.string.category_third));

        new TouristAttraction(getString(R.string.varna_roman_baths), R.drawable.varna_roman_baths, getString(R.string.varna_roman_baths_description), getString(R.string.city_two), getString(R.string.category_third));
        new TouristAttraction(getString(R.string.varna_opera_theatre), R.drawable.varna_opera_theatre, getString(R.string.varna_opera_theatre_description), getString(R.string.city_two), getString(R.string.category_first));
        new TouristAttraction(getString(R.string.varna_dolphinarium), R.drawable.varna_dolphinarium, getString(R.string.varna_dolphinarium_description), getString(R.string.city_two), getString(R.string.category_fourth));
        new TouristAttraction(getString(R.string.varna_bar_cubo), R.drawable.varna_bar_cubo, getString(R.string.varna_bar_cubo_description), getString(R.string.city_two), getString(R.string.category_second));


        categoryFirst = getString(R.string.category_first);
        categorySecond = getString(R.string.category_second);
        categoryThird = getString(R.string.category_third);
        categoryFourth = getString(R.string.category_fourth);

        cityOne = getString(R.string.city_one);
        cityTwo = getString(R.string.city_two);
        cityThree = getString(R.string.city_three);
        cityFour = getString(R.string.city_four);


        if (savedInstanceState != null) {
            activeCity = savedInstanceState.getString("activeCity");
        } else {
            activeCity = cityOne;
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        headerImage = (ImageView) findViewById(R.id.imageView_header_image);
        setHeaderImage(headerImage);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabsPager = new CategoryTabsPager(getSupportFragmentManager());
        viewPager.setAdapter(tabsPager);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(activeCity);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bulgaria) {
            showInfo(1);
            return true;
        } else if (id == R.id.action_city_one) {
            showInfo(2);
            return true;
        } else if (id == R.id.action_city_two) {
            showInfo(3);
            return true;
        } else if (id == R.id.action_city_three) {
            showInfo(4);
            return true;
        } else if (id == R.id.action_city_four) {
            showInfo(5);
            return true;
        } else if (id == R.id.action_about) {
            showInfo(6);
            return true;
        } else if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //Makes about popup
    private void showInfo(int i) {
        String dialogTitle = "";
        String dialogContent = "";

        switch (i) {
            case 1:
                dialogTitle = getString(R.string.action_bulgaria);
                dialogContent = getString(R.string.about_bulgaria);
                break;
            case 2:
                dialogTitle = getString(R.string.city_one);
                dialogContent = getString(R.string.about_city_one);
                break;
            case 3:
                dialogTitle = getString(R.string.city_two);
                dialogContent = getString(R.string.about_city_two);
                break;
            case 4:
                dialogTitle = getString(R.string.city_three);
                dialogContent = getString(R.string.about_city_three);
                break;
            case 5:
                dialogTitle = getString(R.string.city_four);
                dialogContent = getString(R.string.about_city_four);
                break;
            case 6:
                dialogTitle = getString(R.string.action_about);
                dialogContent = getString(R.string.about_app);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogContent);
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nothig to do... just closes the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_city_one) {
            activeCity = cityOne;
        } else if (id == R.id.nav_city_two) {
            activeCity = cityTwo;
        } else if (id == R.id.nav_city_three) {
            activeCity = cityThree;
        } else if (id == R.id.nav_city_four) {
            activeCity = cityFour;
        }

        //recreates fragments and actionbar with new content
        viewPager.setAdapter(tabsPager);
        getSupportActionBar().setTitle(activeCity);
        setHeaderImage(headerImage);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void addTouristObject(TouristAttraction touristAttraction) {
        touristAttractionArrayList.add(touristAttraction);
    }

    public void setHeaderImage(ImageView headerImage) {

        if (activeCity.equals(getString(R.string.city_one))) {
            headerImage.setImageResource(R.drawable.city_one);
        } else if (activeCity.equals(getString(R.string.city_two))) {
            headerImage.setImageResource(R.drawable.city_two);
        } else if (activeCity.equals(getString(R.string.city_three))) {
            headerImage.setImageResource(R.drawable.city_three);
        } else {
            headerImage.setImageResource(R.drawable.city_four);
        }
        this.headerImage = headerImage;
    }


    public class CategoryTabsPager extends FragmentStatePagerAdapter {

        private CategoryTabsPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CategoryItemsFragment fragment = new CategoryItemsFragment();
            Bundle bundle = new Bundle();

            switch (position) {
                case 0:
                    bundle.putInt("color", Color.argb(255, 255, 255, 255));
                    bundle.putParcelableArrayList("itemsList", getItemsLIstByCityAndCategory(activeCity, categoryFirst));
                    break;
                case 1:
                    bundle.putInt("color", Color.argb(64, 230, 230, 230));
                    bundle.putParcelableArrayList("itemsList", getItemsLIstByCityAndCategory(activeCity, categorySecond));
                    break;
                case 2:
                    bundle.putInt("color", Color.argb(255, 255, 255, 255));
                    bundle.putParcelableArrayList("itemsList", getItemsLIstByCityAndCategory(activeCity, categoryThird));
                    break;
                case 3:
                    bundle.putInt("color", Color.argb(64, 230, 230, 230));
                    bundle.putParcelableArrayList("itemsList", getItemsLIstByCityAndCategory(activeCity, categoryFourth));
                    break;
            }

            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return categoryFirst;

                case 1:
                    return categorySecond;

                case 2:
                    return categoryThird;

                case 3:
                    return categoryFourth;
            }
            return null;
        }
    }


    private static ArrayList<TouristAttraction> getItemsLIstByCityAndCategory(String activeCity, String category) {
        ArrayList<TouristAttraction> list = new ArrayList<>();

        for (TouristAttraction touristAttraction : touristAttractionArrayList) {
            if (touristAttraction.getCity().equals(activeCity) && touristAttraction.getCategory().equals(category)) {
                list.add(touristAttraction);
            }
        }
        return list;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("activeCity", activeCity);
        super.onSaveInstanceState(outState);
    }

}
