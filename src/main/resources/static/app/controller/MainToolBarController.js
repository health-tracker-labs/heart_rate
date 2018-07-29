Ext.define('app.controller.MainToolBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.MainToolBarController',

    onLogoutClick: function () {
        window.open("../logout", "_self");
    },

    onRefreshClick: function () {
        var me = this;
        var toolBar = this.getView();
        var text = "Refresh button will be disabled for 10 minutes. Do you want to refresh pressure data?";
        var button = me.getView().getReferences().refreshButton;
        Ext.Msg.confirm("Confirmation", text, function (btnText) {
            if (btnText === "yes") {
                button.disable();
                toolBar.cleanToolBarComponents();
                Ext.Ajax.request({
                    url: 'http://localhost:8080/heart_rate/pressure/pull.do',
                    method: 'POST',
                    success: function (response) {
                        me.fireEvent('onReloadChartStoreAndDisableRefreshButton', me);
                    },
                    failure: function (response) {
                        button.enable();
                        alert("Error: " - response.responseText);
                    }
                });
            }
        }, this);
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
            this.fireEvent('onReloadChartStore', personId, from, to);
            toolBar.cleanToolBarComponents();
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
        }
    }
});
