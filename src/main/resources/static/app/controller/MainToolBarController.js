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
            url: '../status/getService',
            method: 'GET',
            success: function (response) {
                var obj = JSON.parse(response.responseText);
                me.callService(obj);
            },
            failure: function (response) {
                button.enable();
            }
        });
    },

    onSearchClick: function () {
        var me = this;

        var toolBar = this.getView();
		var references = toolBar.getReferences();
        var fromDateField = references.from_date;
        var toDateField = references.to_date;

        var from = fromDateField.getValue();
        var to = toDateField.getValue();
        var personId = references.personCombobox.getValue();

        if (from > to && to != null) {
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
            toolBar.getReferences().from_date.reset();
            toolBar.getReferences().to_date.reset();
            toolBar.getReferences().personCombobox.reset();
        },
        callService: function (response) {
            var eventName = 'on' + response.serviceName + 'Refresh';
            if (response.serviceName === "None") {
                Ext.Msg.alert('Failed', "You will be able to refresh data in " + response.timeBeforeAvailability);
                this.getView().lookupReference('refreshButton').enable();
            } else {
                this.fireEvent(eventName, this);
            }
        }
    }
});
