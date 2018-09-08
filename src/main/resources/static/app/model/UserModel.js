Ext.define('app.model.UserModel', {
    extend: 'Ext.data.Model',
    alias: 'UserModel',

    fields: [
        'username',
        'password',
        { name: 'state', type: 'bool' }
    ]
});