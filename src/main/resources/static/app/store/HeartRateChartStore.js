Ext.define('app.store.HeartRateChartStore', {
    extend: 'Ext.data.Store',

    fields: ['date', 'lower', 'upper', 'beatsPerMinute'],

    proxy: {
        type: 'ajax',
        url : '/heartRate/chart.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});