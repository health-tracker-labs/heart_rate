Ext.define('app.store.HeartRateChartStore', {
    extend: 'Ext.data.Store',

    fields: ['lower', 'upper', 'beatsPerMinute', 'weatherPressure'],

    proxy: {
        type: 'ajax',
        url : '../heartRate/chart.json',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});