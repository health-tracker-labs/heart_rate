Ext.define('app.store.PersonStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : false
});