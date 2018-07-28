Ext.define('app.controller.MainToolBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.MainToolBarController',

    onLogoutClick: function () {
        window.open("../logout", "_self");
    },

    onRefreshClick: function () {
        var me = this;
        var button = me.getView().getReferences().refreshButton;
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/pressure/pull.do',
            method: 'POST',
            success: function (transport) {
                alert("entered");
                me.fireEvent('onRefresh');
                setTimeout(function () {
                    button.enable()
                }, 1000 * 60 * 5);
            },
            failure: function (transport) {
                alert("Error: " - transport.responseText);
            }
        });
    },

    onSearchClick: function () {
        var me = this;

        var toolBar = this.getView();
        var fromDateField = toolBar.getReferences().from_date;
        var toDateField = toolBar.getReferences().to_date;
        var from = fromDateField.getValue();
        var to = toDateField.getValue();
        var personId = toolBar.getReferences().personCombobox.getValue();

        if (from > to) {
            Ext.Msg.alert('Failed', 'From date is later than to date');
        } else if (toolBar.isValid()) {
            this.fireEvent('onRefresh', personId, from, to);
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
        }
    }
});
