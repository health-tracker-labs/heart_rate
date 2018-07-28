Ext.define('app.controller.AddHeartRateFormController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.AddHeartRateFormController',

    onSubmitButtonClick: function () {
        var me = this;
        var form = me.getView();
        if (form.isValid()) {
            form.submit({
                success: function (form, action) {
                    Ext.Msg.alert('Success', 'Save success');
                    me.fireEvent('onRefresh');
                    form.reset();
                },
                failure: function (form, action) {
                    Ext.Msg.alert('Failed', 'Save failed');
                }
            });
        }
    }
});