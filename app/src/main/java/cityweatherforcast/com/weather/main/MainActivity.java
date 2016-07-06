package cityweatherforcast.com.weather.main;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cityweatherforcast.com.weather.R;
import cityweatherforcast.com.weather.bean.Weather;
import cityweatherforcast.com.weather.task.FetchCityData;
import cityweatherforcast.com.weather.task.FetchWeatherData;

import static cityweatherforcast.com.weather.utils.CONSTANTS.APPID;
import static cityweatherforcast.com.weather.utils.CONSTANTS.WEATHERURL;

public class MainActivity extends AppCompatActivity implements OnReceiveForecast, OnCityListReceived, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private AutoCompleteTextView autoCompleteTextView;
    private RecyclerView weatherList;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Button currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        initializeUI();
        setUpUI();
    }

    private void initializeUI() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.cityname);
        autoCompleteTextView.setThreshold(1);
        weatherList = (RecyclerView) findViewById(R.id.recycle_list);
        currentLocation = (Button) (findViewById(R.id.currentLocation));
    }

    private void setUpUI() {
        FetchCityData fetchCityData = new FetchCityData(MainActivity.this, getResources().openRawResource(R.raw.city));
        fetchCityData.execute();

        (findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    URL url = new URL(WEATHERURL + "/data/2.5/forecast/daily?q=" + autoCompleteTextView.getText() + "&mode=json&units=metric&cnt=14&APPID=" + APPID);
                    FetchWeatherData fetchWeatherData = new FetchWeatherData(MainActivity.this, url);
                    fetchWeatherData.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    try {
                        URL url = new URL(WEATHERURL + "/data/2.5/forecast/daily?lat="
                                + mLastLocation.getLatitude() + "&lon="
                                + mLastLocation.getLongitude() + "&mode=json&units=metric&cnt=14&APPID=" + APPID);
                        FetchWeatherData fetchWeatherData = new FetchWeatherData(MainActivity.this, url);
                        fetchWeatherData.execute();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRecieveForecast(List<Weather> weather) {
        if(weather != null && weather.size() > 0) {
            Toast.makeText(getBaseContext(), "Weather updated", Toast.LENGTH_SHORT).show();
            autoCompleteTextView.setText("");
            weatherList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            weatherList.setAdapter(new RecyclerAdapter(weather));
        } else {
            Toast.makeText(getBaseContext(), "Weather details couldn't be fetched", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation.setEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        currentLocation.setEnabled(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        currentLocation.setEnabled(false);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void cityList(List<String> cities) {
        if (cities != null) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, cities);
            autoCompleteTextView.setAdapter(adapter);
        }
    }
}
