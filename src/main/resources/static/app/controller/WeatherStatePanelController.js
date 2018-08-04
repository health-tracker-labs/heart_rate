Ext.define('app.controller.WeatherStatePanelController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.WeatherStatePanelController',

    onRender: function () {
        var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/pressure/getTodayWeatherUrl.json',
            method: 'GET',
            success: function (response) {
                me.update(response);
            },
            failure: function (response) {
                Ext.Msg.alert('Error!', 'Can not get today weather data');
            }
        });
    },

    privates: {
        update: function (response) {
            var panel = this.getView();
            var image = panel.lookupReference('weatherStateImg');
            var label = panel.lookupReference('temperatureLabel');
            var weatherResponse = JSON.parse(response.responseText);

            var description = weatherResponse.description;
            image.setSrc(weatherResponse.iconUrl);
            label.setHtml(weatherResponse.temperature);
            Ext.create('Ext.tip.ToolTip', {
                target: image.getEl(),
                html: description
            });
        }
    }
});