Ext.define('app.view.AddUserForm', {
    extend: 'Ext.form.Panel',

    renderTo: Ext.getBody(),

    buttons: [{
        text: 'Submit',
        disabled: true
    }]
});