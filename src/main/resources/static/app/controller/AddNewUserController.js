Ext.define('app.controller.AddNewUserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.AddNewUserController',

    onAddUserButtonClicked: function () {
        //Ext.get('view.MainView').destroy();
        this.getView().up().destroy();
        Ext.create('app.view.AddUserForm');
    }
});