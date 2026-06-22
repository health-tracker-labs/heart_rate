Ext.define('app.store.WeightChartStore', {
    extend: 'Ext.data.Store',

    fields: ['weight'],

    proxy: {
        type: 'ajax',
        url : '../weights',
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    },
    autoLoad : true
});