Ext.define('app.store.DateDataStore', {
    extend: 'Ext.data.Store',

    proxy: {
        type: 'ajax',
        url : '../heartRate/getByDateRangeAndPerson.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : false
});