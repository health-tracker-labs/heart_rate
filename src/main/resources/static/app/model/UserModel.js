Ext.define('app.model.UserModel', {
    extend: 'Ext.data.Model',
    alias: 'UserModel',

    fields: [
        {name: 'username', type: 'string'},
        {name: 'password', type: 'string'},
        {name: 'state', type: 'bool'},
        {name: 'roles', type: 'auto'}
    ]
});