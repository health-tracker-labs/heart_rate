Ext.define('app.view.MainToolBar', {
    extend: 'Ext.form.Panel',

    requires: [
        'app.controller.MainToolBarController'
    ],
    alias: 'widget.view.myToolbar',
    controller: 'MainToolBarController',

    items: [{
        xtype: 'toolbar',
        dock: 'right',
        items: [
            //'',
            {
                text: 'logout',
                handler: 'onLogoutClick'
            }
        ]
    }]
});