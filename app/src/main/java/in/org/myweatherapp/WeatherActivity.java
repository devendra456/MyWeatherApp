package in.org.myweatherapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.org.myweatherapp.models.City;
import in.org.myweatherapp.models.Coord;
import in.org.myweatherapp.models.DateModel;
import in.org.myweatherapp.models.List;

public class WeatherActivity extends AppCompatActivity {

    private ImageView today_weather_icon;
    private TextView city_name, country_name, low_temp, high_temp, temp, weather_description,
            weather_status, feels_like, humidity, sea_level, wind_speed, visibility;
    private RecyclerView recyclerView;
    private java.util.List<DateModel> dateModelList;
    private Bundle bundle;
    private ProgressBar progressBar;
    private double latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_weather2);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        initViews();
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        GpsTracker gpsTracker = new GpsTracker(WeatherActivity.this);
        if (gpsTracker.canGetLocation()) {
             latitude = gpsTracker.getLatitude();
             longitude = gpsTracker.getLongitude();
            City city=new City();
            Coord coord=new Coord();
            coord.setLat((float) latitude);
            coord.setLon((float) longitude);
            city.setCoord(coord);
            showWeatherData(latitude, longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        bundle=savedInstanceState;
    }

    private void initViews() {
        today_weather_icon = findViewById(R.id.today_weather_image_view);
        city_name = findViewById(R.id.city_name_view);
        country_name = findViewById(R.id.country_name_view);
        low_temp = findViewById(R.id.low_temperature);
        high_temp = findViewById(R.id.high_temperature);
        temp = findViewById(R.id.temperature);
        weather_description = findViewById(R.id.today_weather_description_text_view);
        weather_status = findViewById(R.id.today_weather_status_text_view);
        humidity = findViewById(R.id.humidity);
        sea_level = findViewById(R.id.sea_level);
        feels_like = findViewById(R.id.feel_like);
        wind_speed = findViewById(R.id.wind_speed);
        visibility = findViewById(R.id.visibility);
        recyclerView = findViewById(R.id.days);
        progressBar = findViewById(R.id.progress);
        dateModelList = new ArrayList<>();
    }

    private void showWeatherData(double latitude, double longitude) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        /*JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=4cd48b415bb84c6e29ed68ec323b4645", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONArray listJsonArray = response.getJSONArray("list");
                        JSONObject cityObject = response.getJSONObject("city");
                        //City city=new Gson().fromJson(cityObject.toString(),City.class);
                        //Log.d("TAG", "onResponse: "+city.getCountry()+""+city.getName());
                        Log.d("TAG", "onResponse: "+listJsonArray);

                    } catch (JSONException e) {
                        Log.d("TAG", "onNoResponse: " + e.getMessage());
                    }

                } else {
                    Log.d("TAG", "response null ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=4cd48b415bb84c6e29ed68ec323b4645", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray listJsonArray = jsonObject.getJSONArray("list");
                        JSONObject cityObject = jsonObject.getJSONObject("city");
                        City city = new Gson().fromJson(cityObject.toString(), City.class);
                        for (int i = 0; i < listJsonArray.length(); i++) {
                            JSONObject jsonObject1 = listJsonArray.getJSONObject(i);
                            List list = new Gson().fromJson(jsonObject1.toString(), List.class);
                            dateModelList.add(new DateModel(list.getWeather().get(0).getIcon(), list.getDt_txt()));

                        }
                        RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(dateModelList,WeatherActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        recyclerView.setAdapter(recyclerViewAdapter);
                        JSONObject listObject = listJsonArray.getJSONObject(0);
                        List list = new Gson().fromJson(listObject.toString(), List.class);
                        Glide.with(WeatherActivity.this).load("https://openweathermap.org/img/w/" + list.getWeather().get(0).getIcon() + ".png").into(today_weather_icon);
                        weather_description.setText(list.getWeather().get(0).getDescription());
                        weather_status.setText(list.getWeather().get(0).getMain());
                        city_name.setText(city.getName());
                        country_name.setText(city.getCountry());
                        float t = list.getMain().getTemp();
                        int t1 = (int) (t - 273);
                        float tMax = list.getMain().getTempMax();
                        int tMax1 = (int) (tMax - 273);
                        float tMin = list.getMain().getTempMin();
                        int tMin1 = (int) (tMin - 273);
                        float tFelt = list.getMain().getFeelsLike();
                        int tFelt1 = (int) (tFelt - 273);
                        temp.setText(String.valueOf(t1));
                        high_temp.setText(String.valueOf(tMax1));
                        low_temp.setText(String.valueOf(tMin1));
                        feels_like.setText(tFelt1+ " C");
                        humidity.setText(list.getMain().getHumidity().toString() + " %");
                        sea_level.setText(list.getMain().getSea_level().toString() + " hPa");
                        wind_speed.setText(list.getWind().getSpeed().toString() + " km/h");
                        visibility.setText(list.getVisibility().toString() + " m");
                        progressBar.setVisibility(View.INVISIBLE);


                    } catch (JSONException e) {
                        Log.d("TAG", "onNoResponse: " + e.getMessage());
                        Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        showWeatherData(latitude,longitude);
                    }

                } else {
                    Toast.makeText(WeatherActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "response null ");
                    showWeatherData(latitude,longitude);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
                showWeatherData(latitude,longitude);
            }
        });
        requestQueue.add(stringRequest);
    }

    public void reLoad(View view) {
       progressBar.setVisibility(View.VISIBLE);
        showWeatherData(latitude,longitude);
    }
}