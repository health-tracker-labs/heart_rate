Ext.define('app.view.MainView', {
    extend: 'Ext.container.Viewport',

    requires: [
        'app.view.MainToolBar',
        'app.view.AddHeartRateForm'
    ],

    renderTo: Ext.getBody(),
    layout: 'border',

    items: [
        {
            region: 'north',
            xtype: 'view.MainToolBar'
        },
        {
            region: 'center',
            layout: 'fit'
        },
        {
            region: 'south',
            collapsible: true,
            xtype: 'view.AddHeartRateForm'
        }
    ]
});