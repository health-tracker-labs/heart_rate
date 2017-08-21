Ext.define('app.store.HeartRateStore', {
    extend: 'Ext.data.Store',

    fields: ['date', 'lower', 'upper' ],

    proxy: {
        type: 'ajax',
        url : 'heartRate/chart.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});