Ext.define('app.controller.MainToolBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.MainToolBarController',

    onLogoutClick: function () {
        window.open("../logout", "_self");
    },

    onSearchClick: function () {
        var toolBar = this.getView();
        var from = toolBar.getReferences().from_date.getValue();
        var to = toolBar.getReferences().to_date.getValue();
        var chart = Ext.ComponentQuery.query("#chart")[0];
        var personId = toolBar.getReferences().personCombobox.getValue();
        if (toolBar.isValid()) {
            chart.getStore().load({
                params: {
                    personId: personId,
                    from: from,
                    to: to
                }
            });
            chart.redraw();
        } else {
            Ext.Msg.alert('Failed', 'You entered invalid dates');
        }
    }
});