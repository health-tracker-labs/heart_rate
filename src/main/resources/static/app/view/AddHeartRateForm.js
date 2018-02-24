Ext.define('app.view.AddHeartRateForm', {
    extend: 'Ext.form.Panel',

    requires : [
        'app.controller.AddHeartRateFormController'
    ],

    controller: 'AddHeartRateFormController',

    width: 1440,
    title: 'Add Heart Rate',
    renderTo: Ext.getBody(),

    url: '/heartRate/save.do',

    defaults: {
        anchor: '100%',
        allowBlank: false
    },

    defaultType: 'numberfield',
    items: [{
        fieldLabel: 'Upper',
        name: 'upperPressure'
    }, {
        fieldLabel: 'Lower',
        name: 'lowerPressure'
    }, {
        fieldLabel: 'BPM',
        name: 'beatsPerMinute'
    }, {
        xtype: 'combobox',
        reference: 'personCombobox',
        fieldLabel: 'Person',
        store: Ext.create('app.store.PersonStore'),
        queryMode: 'local',
        displayField: 'name',
        valueField: 'id',
        name: 'personId'
    },
        {
            xtype: 'component',
            autoEl: {
                tag: 'a',
                href: 'http://localhost:8080/logout',
                html: 'logout'
            }
        }],
    buttons: [{
        text: 'Refresh',
        handler: 'onResetButtonClick'
    }, {
        text: 'Submit',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        handler: 'onSubmitButtonClick'
    }]
});