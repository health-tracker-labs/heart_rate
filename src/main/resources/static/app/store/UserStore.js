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
            update: '../user/update',
            read: '../user/getAll.json'
        },
        writer: {
            type: 'json',
            writeAllFields: true
        }
    },
    autoLoad: true
});