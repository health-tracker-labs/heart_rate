package com.sergtm.model;

public class WeatherResponse {
    private String iconUrl;
    private long temperature;
    private String description;

    private WeatherResponse(Builder builder) {
        iconUrl = builder.iconUrl;
        temperature = builder.temperature;
        description = builder.description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public long getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private static final String OPEN_WEATHER_MAP_URL = "http://openweathermap.org/img/w/%s.png";
        private String iconUrl;
        private long temperature;
        private String description;

        public Builder setIconUrl(String iconUrl) {
            this.iconUrl = String.format(OPEN_WEATHER_MAP_URL, iconUrl);
            return this;
        }

        public Builder setTemperature(double temperature) {
            this.temperature = Math.round(temperature);
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public WeatherResponse build(){
            return new WeatherResponse(this);
        }
    }
}
