package cityweatherforcast.com.weather.utils;


public enum CONSTANTS {

    WEATHERURL("http://api.openweathermap.org"),
    APPID("8edabca919c745ef300401454fd6800c");

    private String value;

    private CONSTANTS(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
