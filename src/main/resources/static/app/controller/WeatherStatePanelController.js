Ext.define('app.controller.WeatherStatePanelController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.WeatherStatePanelController',

    listen: {
        controller: {
            'MainToolBarController': {
                onWeatherServiceRefresh: 'weatherServiceRefresh'
            }
        }
    },

    weatherServiceRefresh: function (toolBar) {
        var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/weather/getTodayWeatherUrl.json',
            method: 'GET',
            success: function (response) {
                var button = toolBar.getView().lookupReference('refreshButton');
                me.update(response);
                button.enable();
            },
            failure: function (response) {
                Ext.Msg.alert('Error!', 'Can not get today weather data');
            }
        });
    },

    onRender: function () {
        var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/weather/getOnRender.json',
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
            var weatherResponse = JSON.parse(response.responseText);
            var description = weatherResponse.description;

            var label = panel.lookupReference('temperatureLabel');
            label.setHtml(weatherResponse.temperature);

            var image = panel.lookupReference('weatherStateImg');
            image.setSrc(weatherResponse.iconUrl);
            //image.setTooltip(description);

            var descriptionLabel = panel.lookupReference('weatherDescription');
            descriptionLabel.setHtml(description);
        }
    }
});