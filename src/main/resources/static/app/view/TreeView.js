Ext.define('app.view.TreeView', {
    extend: 'Ext.tree.Panel',
    alias:'widget.TreeView',

    requires : [
        'app.store.TreeViewStore',
        'app.controller.TreeViewController'
    ],

    controller: 'TreeViewController',

    store: Ext.create('app.store.TreeViewStore'),

    rootVisible: false,
    root: {
        expanded: true
    },

    listeners: {
        itemclick: 'onItemClick'
    }
});