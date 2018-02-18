Ext.define('app.store.PersonStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        url : '/person/getAll.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});