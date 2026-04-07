Ext.define('app.store.TimeOfDayStore', {
    extend: 'Ext.data.Store',

    storeId: 'timeOfDayStore',
    fields: ['value', 'label'],
    data: [
        { value: 'MORNING', label: 'Morning' },
        { value: 'DAY', label: 'Day' },
        { value: 'EVENING', label: 'Evening' }
    ]
});