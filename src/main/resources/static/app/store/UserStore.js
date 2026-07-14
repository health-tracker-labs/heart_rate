Ext.define('app.store.UserStore', {
    extend: 'Ext.data.Store',

    model: 'app.model.UserModel',

    proxy: {
        type: 'ajax',
        api: {
            read: '../users',
            update: '../users'
        },
        actionMethods: {
            read: 'GET',
            update: 'PUT'
        },
        reader: {
            type: 'json',
            rootProperty: 'data'
        },
        writer: {
            type: 'json',
            writeAllFields: true,
            transform: function (data, request) {
                if (request.getAction() === 'update') {
                    delete data.roles;
                }

                return data;
            }
        }
    },
    autoLoad: true
});