package cityweatherforcast.com.weather.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cityweatherforcast.com.weather.bean.Weather;
import cityweatherforcast.com.weather.factory.WeatherParserFactory;
import cityweatherforcast.com.weather.main.OnReceiveForecast;
import cityweatherforcast.com.weather.parser.IWeatherParser;


public class FetchWeatherData extends AsyncTask<Void, Void, List<Weather>> {

    private OnReceiveForecast onReceiveForecast;
    private URL url;

    public FetchWeatherData(OnReceiveForecast onReceiveForecast, URL url) {
        this.onReceiveForecast = onReceiveForecast;
        this.url = url;
    }

    @Override
    protected List<Weather> doInBackground(Void... voids) {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inStream = urlConnection.getInputStream();
            IWeatherParser weatherParser = WeatherParserFactory.getParser(WeatherParserFactory.JSONWEATHERPARSER);
            return weatherParser.parse(inStream);

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
    protected void onPostExecute(List<Weather> weatherArray) {
        super.onPostExecute(weatherArray);
        if(onReceiveForecast != null) {
            onReceiveForecast.onRecieveForecast(weatherArray);
        }
    }
}
