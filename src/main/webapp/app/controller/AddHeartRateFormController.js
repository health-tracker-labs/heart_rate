Ext.define('app.controller.AddHeartRateFormController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.AddHeartRateFormController',

    onResetButtonClick: function() {
        var form = this.getView();
        form.reset();
        location.reload();

    },

    onSubmitButtonClick: function() {
        var form = this.getView();
        if (form.isValid()) {

            form.submit({
                success: function(form, action) {
                   Ext.Msg.alert('Success', 'Save success');
                   var chart = Ext.ComponentQuery.query("#chart")[0];
                   chart.getStore().load();
                   chart.redraw();
                },
                failure: function(form, action) {
                    Ext.Msg.alert('Failed', 'Save failed');
                }
            });
        }
    }
});