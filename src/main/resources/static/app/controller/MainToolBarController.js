Ext.define('app.controller.MainToolBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.MainToolBarController',

    onLogoutClick: function () {
        window.open("../logout", "_self");
    },

    onRefreshClick: function () {
        var me = this;
        var button = me.getView().lookupReference('refreshButton');
        button.disable();
        me.cleanToolBarComponents();
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/status/getService',
            method: 'GET',
            success: function (response) {
                var resText = response.responseText.replace(/\"/g, "");
                me.callService(resText);
            },
            failure: function (response) {
                button.enable();
            }
        });
    },

    onSearchClick: function () {
        var me = this;

        var toolBar = this.getView();
        var fromDateField = toolBar.lookupReference('from_date');
        var toDateField = toolBar.lookupReference('to_date');
        var from = fromDateField.getValue();
        var to = toDateField.getValue();
        var personId = toolBar.lookupReference('personCombobox').getValue();

        if (from > to) {
            Ext.Msg.alert('Failed', 'From date is later than to date');
        } else if (toolBar.isValid()) {
            this.fireEvent('onReloadChartStore', personId, from, to);
        } else {
            me.validateRangeDates(fromDateField, toDateField);
        }
    },

    privates: {
        validateRangeDates: function (fromDateField, toDateField) {
            if (!fromDateField.isValid() && !toDateField.isValid()) {
                Ext.Msg.alert('Failed', 'You entered invalid from and to dates');
            } else if (!fromDateField.isValid()) {
                Ext.Msg.alert('Failed', 'You entered invalid from date');
            } else if (!toDateField.isValid()) {
                Ext.Msg.alert('Failed', 'You entered invalid to date');
            }
        },
        cleanToolBarComponents: function () {
            var toolBar = this.getView();
            toolBar.lookupReference('from_date').reset();
            toolBar.lookupReference('to_date').reset();
            toolBar.lookupReference('personCombobox').reset();
        },
        callService: function (response) {
            if (response === "None") {
                Ext.Msg.alert('Failed', "Service is unavailable now. Try in 10 minutes");
                this.getView().lookupReference('refreshButton').enable();
            } else if (response === 'WeatherService') {
                this.fireEvent('onRedrawWithButton', this);
            } else {
                this.fireEvent('onReloadChartStoreAndDisableRefreshButton', this);
            }
        }
    }
});
