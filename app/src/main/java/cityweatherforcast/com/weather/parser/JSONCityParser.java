package cityweatherforcast.com.weather.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cityweatherforcast.com.weather.bean.City;

public class JSONCityParser implements ICityParser {
    @Override
    public List<City> parse(InputStream inputStream) throws IOException, JSONException {

        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder json = new StringBuilder();
        while ((line = bReader.readLine()) != null) {
            json.append(line);
        }

        List<City> cityArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            City city = new City();
            city.setCityId(jsonObject.getInt("_id") + "");
            city.setCityName(jsonObject.getString("name"));
            city.setCountry(jsonObject.getString("country"));
            JSONObject cObject = jsonObject.getJSONObject("coord");
            city.setLatitude(cObject.getDouble("lat") + "");
            city.setLongitude(cObject.getDouble("lon") + "");
            cityArray.add(city);
        }
        return cityArray;
    }
}
