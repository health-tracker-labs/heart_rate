Ext.define('app.view.MainToolBar', {
    extend: 'Ext.form.Panel',

    requires: [
        'app.controller.MainToolBarController'
    ],
    alias: 'widget.view.myToolbar',
    controller: 'MainToolBarController',

    items: [{
        xtype: 'toolbar',
        items: [{
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
            xtype: 'combobox',
            reference: 'personCombobox',
            emptyText: 'person',
            store: Ext.create('app.store.PersonStore'),
            queryMode: 'local',
            displayField: 'name',
            valueField: 'id',
            name: 'personId'
        }, '-',{
            text: 'sort',
            handler: 'onSearchClick'
        }, '->', {
            text: 'logout',
            handler: 'onLogoutClick'
        }]
    }]
});