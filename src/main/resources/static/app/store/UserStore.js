Ext.define('app.store.UserStore', {
    extend: 'Ext.data.Store',

    model: 'app.model.UserModel',

    proxy: {
        type: 'ajax',
        reader: {
            type: 'json',
            rootProperty: 'data'
        },
        api: {
            update: '../users/update',
            read: '../users'
        },
        writer: {
            type: 'json',
            writeAllFields: true
        }
    },
    autoLoad: true
});