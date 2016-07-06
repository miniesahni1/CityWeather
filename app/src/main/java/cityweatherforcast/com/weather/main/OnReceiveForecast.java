package cityweatherforcast.com.weather.main;

import java.util.List;

import cityweatherforcast.com.weather.bean.Weather;


public interface OnReceiveForecast {
    void onRecieveForecast(List<Weather> weatherArray);
}
