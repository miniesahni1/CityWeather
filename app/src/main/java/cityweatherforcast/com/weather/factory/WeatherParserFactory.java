package cityweatherforcast.com.weather.factory;

import cityweatherforcast.com.weather.parser.IWeatherParser;
import cityweatherforcast.com.weather.parser.JSONWeatherParser;

public class WeatherParserFactory {

    public static final String JSONWEATHERPARSER = "JSONWEATHERPARSER";

    public static final IWeatherParser getParser(String type) {
        switch (type) {
            case JSONWEATHERPARSER :
                return new JSONWeatherParser();
        }
        return null;
    }

}
