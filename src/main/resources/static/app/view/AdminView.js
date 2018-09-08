Ext.define('app.view.AdminView', {
    extend: 'Ext.Container',
    requires: [
        /*'Ext.Button',
        'Ext.list.Tree',*/
        'app.view.TreeView'
    ],
    renderTo:Ext.getBody(),

    items: [{
        xtype: 'panel',
        referenceHolder: true,
        layout: {
            type: 'hbox',
            align: 'stretch'
        },
        items: [{
            xtype: 'TreeView',
            width: '20%',
            reference: 'navigationTree'
        },{
            xtype: 'panel',
            flex: 1,
            reference: 'content'
        }]
    }]
});