Ext.define('app.store.RoleStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        url : '../role/getRoles.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});