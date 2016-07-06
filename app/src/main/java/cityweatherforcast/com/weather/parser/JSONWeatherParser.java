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

import cityweatherforcast.com.weather.bean.Weather;


public class JSONWeatherParser implements IWeatherParser {
    @Override
    public List<Weather> parse(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder json = new StringBuilder();
        while ((line = bReader.readLine()) != null) {
            json.append(line);
        }

        ArrayList<Weather> weatherList = new ArrayList<>();
        JSONObject jsonObjectList = new JSONObject(json.toString());
        if (jsonObjectList.has("list")) {
            JSONArray jsonArray = jsonObjectList.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Weather weatherBean = new Weather();
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (jsonObject.has("temp")) {
                        JSONObject tempObject = jsonObject.getJSONObject("temp");
                        if (tempObject.has("dt")) {
                            weatherBean.setDate(tempObject.getLong("day") + "");
                        }
                        if (tempObject.has("day")) {
                            weatherBean.setDayTemp(tempObject.getDouble("day") + "");
                        }
                        if (tempObject.has("min")) {
                            weatherBean.setMinTemp(tempObject.getDouble("min") + "");
                        }
                        if (tempObject.has("max")) {
                            weatherBean.setMaxTemp(tempObject.getDouble("max") + "");
                        }
                    }
                    if (jsonObject.has("pressure")) {
                        weatherBean.setPressure(jsonObject.getDouble("pressure") + "");
                    }
                    if (jsonObject.has("humidity")) {
                        weatherBean.setHumidity(jsonObject.getDouble("humidity") + "");
                    }
                    if (jsonObject.has("weather")) {
                        JSONArray weatherArray = jsonObject.getJSONArray("weather");
                        if (weatherArray.length() > 0) {
                            JSONObject weatherDescObject = weatherArray.getJSONObject(0);
                            if (weatherDescObject.has("main")) {
                                weatherBean.setMain(weatherDescObject.getString("main"));
                            }
                            if (weatherDescObject.has("description")) {
                                weatherBean.setDescription(weatherDescObject.getString("description"));
                            }
                            if (weatherDescObject.has("icon")) {
                                weatherBean.setIcon(weatherDescObject.getString("icon"));
                            }
                        }
                    }
                    if (jsonObject.has("speed")) {
                        weatherBean.setWindSpeed(jsonObject.getDouble("speed") + "");
                    }
                    weatherList.add(weatherBean);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return weatherList;
    }
}
