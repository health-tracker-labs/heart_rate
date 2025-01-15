Ext.define('app.view.MainToolBar', {
    extend: 'Ext.form.Panel',

    requires: [
        'app.controller.MainToolBarController',
        'app.view.WeatherStatePanel',
		'app.view.ChartTypeCombobox',
		'app.view.PersonCombobox'
    ],
    controller: 'MainToolBarController',
    alias: 'widget.view.MainToolBar',

    items: [{
        xtype: 'toolbar',
        items: [{
            xtype: 'chartTypeCombobox',
            reference: 'chartTypeCombobox',
            name: 'chartTypeId'
		}, {
            xtype: 'datefield',
            anchor: '100%',
            reference: 'from_date',
            name: 'from_date',
            emptyText: 'from'
        }, {
            xtype: 'datefield',
            anchor: '100%',
            reference: 'to_date',
            emptyText: 'to',
            name: 'to_date'
        }, '-', {
            xtype: 'personCombobox',
            reference: 'personCombobox',
            emptyText: 'select person',
            name: 'personId'
        }, '-', {
            text: 'Apply',
            handler: 'onSearchClick'
        }, '-', {
            text: 'Refresh',
            reference: 'refreshButton',
            handler: 'onRefreshClick'
        }, {
            xtype: 'view.WeatherStatePanel'
        }, '->', {
            text: 'logout',
            handler: 'onLogoutClick'
        }]
    }]
});