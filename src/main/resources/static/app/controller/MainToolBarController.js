Ext.define('app.controller.MainToolBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.MainToolBarController',

    onLogoutClick: function () {
        window.open("../logout", "_self");
    },

    onSearchClick: function () {
        var toolBar = this.getView();
        var fromDateField = toolBar.getReferences().from_date;
        var toDateField = toolBar.getReferences().to_date;
        var from = fromDateField.getValue();
        var to = toDateField.getValue();
        var chart = Ext.ComponentQuery.query("#chart")[0];
        var personId = toolBar.getReferences().personCombobox.getValue();
        var redraw = true;

        if (from > to) {
            Ext.Msg.alert('Failed', 'From date is later than to date');
            redraw = false;
        }

        if (toolBar.isValid() && redraw) {
            chart.getStore().load({
                params: {
                    personId: personId,
                    from: from,
                    to: to
                }
            });
            chart.redraw();
        } else {
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