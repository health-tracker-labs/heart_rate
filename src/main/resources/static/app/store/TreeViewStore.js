Ext.define('app.store.TreeViewStore', {
    extend: 'Ext.data.TreeStore',

    requires : [
        'app.model.TreeViewModel'
    ],

    model: 'app.model.TreeViewModel',

    autoLoad: false
});