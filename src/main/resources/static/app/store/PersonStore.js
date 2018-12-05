Ext.define('app.store.PersonStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        url : '../person/getByUser.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});