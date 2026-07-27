Ext.define('app.store.RoleStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        url : '../roles',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});