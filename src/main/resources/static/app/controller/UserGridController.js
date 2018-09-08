Ext.define('app.controller.UserGridController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.UserGridController',

    onRemoveUserClick: function () {
        var me = this.getView();
        var sm = this.getView().getSelectionModel();
        var id = sm.getSelection()[0].data.id;
        Ext.Ajax.request({
            url: '../user/deleteUser',
            method: 'POST',
            params: {
                id: id
            },
            success: function (response) {
                me.store.load();
            },
            failure: function (response) {
            }
        });
    },

    onUpdateClick: function () {
        var store = this.getView().store;
        store.sync({
            success: function() {
                store.commitChanges();
            },
            failure: function() {
                store.rejectChanges();
            }
        });
    },

    onAddUserClick: function () {

    }
});