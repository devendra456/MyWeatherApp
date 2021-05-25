
package in.org.myweatherapp.models;

public class Main {

    private Float temp;
    private Float feels_like;
    private Float temp_min;
    private Float temp_max;
    private Integer pressure;
    private Integer sea_level;
    private Integer grndLevel;
    private Integer humidity;
    private Float tempKf;

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getFeelsLike() {
        return feels_like;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feels_like = feelsLike;
    }

    public Float getTempMin() {
        return temp_min;
    }

    public void setTempMin(Float tempMin) {
        this.temp_min = tempMin;
    }

    public Float getTempMax() {
        return temp_max;
    }

    public void setTempMax(Float tempMax) {
        this.temp_max = tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getSea_level() {
        return sea_level;
    }

    public void setSea_level(Integer sea_level) {
        this.sea_level = sea_level;
    }

    public Integer getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Integer grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getTempKf() {
        return tempKf;
    }

    public void setTempKf(Float tempKf) {
        this.tempKf = tempKf;
    }

}
