Ext.define('app.model.TreeViewModel', {
    extend: 'Ext.data.Model',
    alias: 'model.TreeViewModel',

    fields: [
        { name: 'leaf', type: 'bool' },
        { name: 'text', type: 'string' },
        { name: 'children'}
    ],

    proxy: {
        type: 'ajax',
        url : '../admin/treeView.json'
    }
});