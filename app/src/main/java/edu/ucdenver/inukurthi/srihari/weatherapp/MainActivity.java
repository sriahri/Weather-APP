package edu.ucdenver.inukurthi.srihari.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout home;
    private ProgressBar loading;
    private TextView cityName, temperature, condition;
    private TextInputEditText cityEdit;
    private ImageView background, icon, search;
    private RecyclerView weather;
    ArrayList<WeatherModel> weatherModelArrayList;
    WeatherAdapter weatherAdapter;
    LocationManager locationManager;
    int PERMISSION = 1;
    String cityNameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.Home);
        loading = findViewById(R.id.Loading);
        cityName = findViewById(R.id.CityName);
        temperature = findViewById(R.id.temperature);
        condition = findViewById(R.id.condition);
        cityEdit = findViewById(R.id.EditCityName);
        background = findViewById(R.id.backgroudImage);
        icon = findViewById(R.id.icon);
        search = findViewById(R.id.search);
        weather = findViewById(R.id.weather);
        weatherModelArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherModelArrayList);
        weather.setAdapter(weatherAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        cityNameInput = getCityName(location.getLongitude(), location.getLatitude());
        getWeatherDetails(cityNameInput);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityEdit.getText().toString();
                if (city.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    cityName.setText(cityNameInput);
                    getWeatherDetails(city);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    String getCityName(double longitude, double latitude) {
        String cityName = "";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);

            for (Address address : addresses) {
                if (address != null) {
                    String city = address.getLocality();
                    if (city != null && !city.equalsIgnoreCase("")) {
                        cityName = city;
                    }
                    }
                else {
                    Log.d("TAG", "CITY NOT FOUND");
                    Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityName;
    }

    void getWeatherDetails(String city) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=814106c5c4d44b4fb69180108231507&q=" + city + "&days=1&aqi=yes&alerts=yes\n";
        cityName.setText(city);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);

                weatherModelArrayList.clear();
                try {
                    String temperatureOutput = response.getJSONObject("current").getString("temp_c");
                    temperature.setText(temperatureOutput.concat("°C"));
                    int isDayTime = response.getJSONObject("current").getInt("is_day");
                    String conditionOutput = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIconOutput = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIconOutput)).into(icon);
                    condition.setText(conditionOutput);
                    Log.i("Info", "Is Day: " + isDayTime);
                    if (isDayTime == 1) {
                        // Picasso.get().load("http:".concat(conditionIconOutput)).into(background);
                        Picasso.get().load("http://images.unsplash.com/photo-1530908295418-a12e326966ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80").into(background);
                        // Picasso.get().load("http://pixabay.com/photos/nature-view-peace-pasture-cloudy-3374058/").into(background);
                        Log.i("Info", "Image loaded: " + isDayTime);
                    } else {
                        Picasso.get().load("http://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80").into(background);
                    }

                    JSONObject forecasting = response.getJSONObject("forecast");
                    JSONObject forecastDay = forecasting.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray forecastHours = forecastDay.getJSONArray("hour");

                    for (int i = 0; i < forecastHours.length(); i++) {
                        JSONObject hour = forecastHours.getJSONObject(i);
                        String time = hour.getString("time");
                        String temp = hour.getString("temp_c");
                        String img = hour.getJSONObject("condition").getString("icon");
                        String wspeed = hour.getString("wind_kph");
                        weatherModelArrayList.add(new WeatherModel(time, temp, img, wspeed));


                    }

                    weatherAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please enter valid city", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}