package com.sergtm.health.tracker.integration.openweather.model.pressure;

public class List {
    private String dt_txt;
    private Main main;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main pressure) {
        this.main = pressure;
    }
}
