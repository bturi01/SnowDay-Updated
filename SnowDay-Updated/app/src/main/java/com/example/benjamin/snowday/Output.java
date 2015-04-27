package com.example.benjamin.snowday;

import org.json.JSONException;

import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.adapter.DailyForecastPageAdapter;
//import com.example.benjamin.snowday.model.Location;
import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.model.WeatherForecast;

import android.content.Intent;
import android.app.ActionBar;
import android.support.v7.widget.*;
import android.widget.*;
import android.view.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import android.location.Location;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.location.Criteria;

import java.util.List;
import java.util.Locale;


/**
 * Created by Chase on 4/23/15.
 */
public class Output extends MainActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.output);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);

        // setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



        //String forecastDaysNum = "3";
        //String city = "London, UK";
        //String lang = "en";

        Intent intent = getIntent();
        String city = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        //JSONWeatherTask task = new JSONWeatherTask();
       // task.execute(new String[]{city,lang});

        //JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        //task1.execute(new String[]{city,lang, forecastDaysNum});

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.skydesc);
        temp = (TextView) findViewById(R.id.temp);
        imgView = (ImageView) findViewById(R.id.condIcon);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }


/*
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {


    @Override
    protected Weather doInBackground(String... params) {
        Weather weather = new Weather();
        String data = ( (new WeatherHttpClient()).getWeatherData(params[0], params[1]));

        try {
            weather = JSONWeatherParser.getWeather(data);
            System.out.println("Weather ["+weather+"]");
            // Let's retrieve the icon
            weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;

    }


    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);

        if (weather.iconData != null && weather.iconData.length > 0) {
            Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
            imgView.setImageBitmap(img);
        }

        cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
        condDescr.setText(weather.currentCondition.getCondition());
        temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
        //hum.setText("" + weather.currentCondition.getHumidity() + "%");
        //press.setText("" + weather.currentCondition.getPressure() + " hPa");
        //windSpeed.setText("" + weather.wind.getSpeed() + " mps");
        //windDeg.setText("" + weather.wind.getDeg() + "°");

    }
}*/

private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

    @Override
    protected WeatherForecast doInBackground(String... params) {

        String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
        WeatherForecast forecast = new WeatherForecast();
        try {
            forecast = JSONWeatherParser.getForecastWeather(data);
            System.out.println("Weather ["+forecast+"]");
            // Let's retrieve the icon
            //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecast;

    }
}
}


