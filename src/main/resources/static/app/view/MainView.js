Ext.define('app.view.MainView', {
    extend: 'Ext.Panel',

    requires: [
        'app.view.MainToolBar',
        'app.view.HeartRateChart',
        'app.view.AddHeartRateForm'
    ],

    renderTo: Ext.getBody(),

    items: [
        {
            xtype: 'view.MainToolBar'
        },
        {
            xtype: 'view.HeartRateChart'
        },
        {
            xtype: 'view.AddHeartRateForm'
        }
    ]
});