package cityweatherforcast.com.weather.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cityweatherforcast.com.weather.bean.City;
import cityweatherforcast.com.weather.factory.CityParserFactory;
import cityweatherforcast.com.weather.main.OnCityListReceived;
import cityweatherforcast.com.weather.parser.ICityParser;


public class FetchCityData extends AsyncTask<Void, Void, List<String>> {

    private OnCityListReceived onCityListRecieved;
    private InputStream inputStream;

    public FetchCityData(OnCityListReceived onCityListRecieved, InputStream inputStream) {
        this.onCityListRecieved = onCityListRecieved;
        this.inputStream = inputStream;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        ICityParser iCityParser = CityParserFactory.getParser(CityParserFactory.JSONCITYPARSER);
        List<City> cities = null;
        try {
            cities = iCityParser.parse(inputStream);
            if (cities != null) {
                ArrayList<String> cityNamesList = new ArrayList<>();
                for (City city : cities) {
                    cityNamesList.add(city.getCityName() + "," + city.getCountry());
                }
                return cityNamesList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<String> cities) {
        super.onPostExecute(cities);
        if (onCityListRecieved != null)
            onCityListRecieved.cityList(cities);
    }
}
