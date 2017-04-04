package com.example.pompeii.marriagehall.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pompeii.marriagehall.R;
import com.example.pompeii.marriagehall.adapter.CategoryListAdapter;
import com.example.pompeii.marriagehall.adapter.MenuListAdapter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeActivity extends NavigationDrawerActivity {

    private Toolbar toolbar;
    private CarouselView home_slider_cv;
    private RecyclerView dashboard_menu_list_rv;
    private Button seller_registration_bt;
    private TextView welcome_msg_tv, readMoreTV;
    private Button searchResultBT;
    private EditText searchET;
    private TextView viewAllServicesTV;
    private RecyclerView category_list_rv;
    private TextView welcomeMessageTitleTV, welcomeMessageBodyTV, captionTagTV;

    private ArrayList<Integer> homeSliderImageList;
    private ArrayList<Integer> menuImageList;
    private ArrayList<String> menuTitleList;
    private ArrayList<Integer> categoryImageList;
    private ArrayList<String> categoryTitleList;
    private ArrayList<String> cityList;
    private Spinner selctCitySP;
    private String cateTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        super.initNavigationDrawer();

        initHomeData();
    }

    private void initHomeData() {

        initImageList();

        viewAllServicesTV = (TextView) findViewById(R.id.viewAllServicesTV);
        readMoreTV = (TextView) findViewById(R.id.readMoreTV);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "font/alexbrush.ttf");
        welcomeMessageTitleTV = (TextView) findViewById(R.id.welcomeMessageTitleTV);
        welcomeMessageBodyTV = (TextView) findViewById(R.id.welcomeMessageBodyTV);
        welcomeMessageTitleTV.setTypeface(typeface1, Typeface.BOLD);

        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "font/marko_one.ttf");
        captionTagTV = (TextView) findViewById(R.id.captionTagTV);
        captionTagTV.setTypeface(typeface2, Typeface.BOLD);

        home_slider_cv = (CarouselView) findViewById(R.id.home_slider_cv);
        home_slider_cv.setImageListener(imageListener);
        home_slider_cv.setPageCount(homeSliderImageList.size());
        home_slider_cv.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        searchET = (EditText) findViewById(R.id.searchET);
        searchResultBT = (Button) findViewById(R.id.searchResultBT);

        category_list_rv = (RecyclerView) findViewById(R.id.category_list_rv);
        GridLayoutManager layoutManager1 = new GridLayoutManager(HomeActivity.this, 3);
        category_list_rv.setLayoutManager(layoutManager1);
        category_list_rv.setHasFixedSize(false);
        category_list_rv.setNestedScrollingEnabled(false);
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(HomeActivity.this, categoryImageList, categoryTitleList);
        category_list_rv.setAdapter(categoryListAdapter);

        for (int i = 0; i < categoryTitleList.size(); i++) {
            cateTitle = categoryTitleList.get(i);
        }
        if (cateTitle.equalsIgnoreCase("Other")) {
            categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OrderListClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent mIntent = new Intent(HomeActivity.this, CustomEventActivity.class);
                    startActivity(mIntent);
                }
            });
        }

        selctCitySP = (Spinner) findViewById(R.id.selctCitySP);
        ArrayAdapter selectCityAdapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, cityList);
        selectCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selctCitySP.setAdapter(selectCityAdapter);

        dashboard_menu_list_rv = (RecyclerView) findViewById(R.id.dashboard_menu_list_rv);
        GridLayoutManager layoutManager2 = new GridLayoutManager(HomeActivity.this, 2);
        dashboard_menu_list_rv.setLayoutManager(layoutManager2);
        dashboard_menu_list_rv.setHasFixedSize(false);
        dashboard_menu_list_rv.setNestedScrollingEnabled(false);
        MenuListAdapter menuListAdapter = new MenuListAdapter(HomeActivity.this, menuImageList, menuTitleList);
        dashboard_menu_list_rv.setAdapter(menuListAdapter);
        menuListAdapter.setOnItemClickListener(new MenuListAdapter.OrderListClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent mIntent = new Intent(HomeActivity.this, VenueListActivity.class);
                startActivity(mIntent);
            }
        });

        // Event listener initialize
        clickListener();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            //imageView.setImageResource(productImageList.get(position));
            Picasso.with(getApplicationContext())
                    .load(homeSliderImageList.get(position))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.banner1)
                    .error(R.drawable.banner2)
                    .fit()
                    .into(imageView);
        }
    };

    public void initImageList() {
        homeSliderImageList = new ArrayList<>();
        homeSliderImageList.add(R.drawable.home_slider_banner);

        menuImageList = new ArrayList<>();
        menuImageList.add(R.drawable.wedding_venue_icon);
        menuImageList.add(R.drawable.mandap_decor_icon);
        menuImageList.add(R.drawable.entertainment_icon);
        menuImageList.add(R.drawable.florists_icon);

        menuTitleList = new ArrayList<>();
        menuTitleList.add("Wedding Venue");
        menuTitleList.add("Mandap Decorators");
        menuTitleList.add("Entertainment");
        menuTitleList.add("Florists");

        categoryImageList = new ArrayList<>();
        categoryImageList.add(R.drawable.coctail_icon);
        categoryImageList.add(R.drawable.birthday_icon);
        categoryImageList.add(R.drawable.wedding_icon);
        categoryImageList.add(R.drawable.conference_icon);
        categoryImageList.add(R.drawable.kity_icon);
        categoryImageList.add(R.drawable.add_icon1);

        categoryTitleList = new ArrayList<>();
        categoryTitleList.add("cocktail Party");
        categoryTitleList.add("Birthday");
        categoryTitleList.add("Wedding");
        categoryTitleList.add("Conference");
        categoryTitleList.add("Kitty Party");
        categoryTitleList.add("Other");

        cityList = new ArrayList<>();
        cityList.add("Select a city");
        cityList.add("Nagpur");
        cityList.add("Pune");
        cityList.add("Amravati");
        cityList.add("Mumbai");
        cityList.add("Akola");
        cityList.add("Gondia");
        cityList.add("Delhi");
        cityList.add("Wardha");
    }

    public void clickListener() {

        searchResultBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(mIntent);
            }
        });

        viewAllServicesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, ServicesActivity.class);
                startActivity(mIntent);
            }
        });

        readMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(mIntent);
            }
        });
    }

}
