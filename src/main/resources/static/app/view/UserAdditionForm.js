Ext.define('app.view.UserAdditionForm', {
    extend: 'Ext.form.Panel',
    xtype: 'form-register',
    controller: 'UserAdditionFormController',

    requires: [
        'app.controller.UserAdditionFormController',
        'app.model.UserModel'
    ],

    frame: true,
    title: 'Add User',
    bodyPadding: 10,
    scrollable: true,
    width: 355,
    maxHeight: 400,
    autoSize: true,

    fieldDefaults: {
        labelAlign: 'right',
        labelWidth: 115,
        msgTarget: 'side'
    },

    items: [{
        xtype: 'fieldset',
        title: 'User Info',
        defaultType: 'textfield',
        defaults: {
            required: true
        },
        items: [{
            allowBlank: false,
            fieldLabel: 'Username',
            name: 'user',
            reference: 'user',
            emptyText: 'username'
        }, {
            allowBlank: false,
            fieldLabel: 'Password',
            name: 'pass',
            reference: 'password',
            emptyText: 'password',
            inputType: 'password'
        }, {
            allowBlank: false,
            xtype: 'checkboxfield',
            reference: 'state',
            fieldLabel: 'State',
            name: 'state'
        },{
            xtype: 'combobox',
            reference: 'role1',
            fieldLabel: 'Role 1',
            store: Ext.create('app.store.RoleStore'),
            queryMode: 'local',
            displayField: 'name',
            valueField: 'id',
            name: 'roleId1',
            editable: false
        },{
            xtype: 'combobox',
            reference: 'role2',
            fieldLabel: 'Role 2',
            store: Ext.create('app.store.RoleStore'),
            queryMode: 'local',
            displayField: 'name',
            valueField: 'id',
            name: 'roleId2',
            required: false,
            editable: false
        }]
    }],

    buttons: [{
        text: 'Submit',
        handler: 'onSubmitClick'
    },{
        text: 'Close',
        handler: 'onCloseClick'
    }],

    renderTo: Ext.getBody()
});