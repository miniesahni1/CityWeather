package cityweatherforcast.com.weather.factory;

import cityweatherforcast.com.weather.parser.ICityParser;
import cityweatherforcast.com.weather.parser.JSONCityParser;

public class CityParserFactory {

    public static final String JSONCITYPARSER = "JSONCityParser";

    public static final ICityParser getParser(String type) {
        switch (type) {
            case JSONCITYPARSER :
                return new JSONCityParser();
        }
        return null;
    }

}
