package cityweatherforcast.com.weather.parser;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cityweatherforcast.com.weather.bean.City;


public interface ICityParser {
    List<City> parse(InputStream inputStream) throws IOException, JSONException;
}
