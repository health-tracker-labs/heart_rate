Ext.define('app.view.AddHeartRateForm', {
    extend: 'Ext.form.Panel',

    requires : [
        'app.controller.AddHeartRateFormController'
    ],

    controller: 'AddHeartRateFormController',

    width: 1440,
    title: 'Add Heart Rate',
    //renderTo: Ext.getBody(),
    alias: 'widget.view.AddHeartRateForm',

    url: '../heartRate/save.do',

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
    }, {
        xtype: 'datefield',
        anchor: '100%',
        fieldLabel: 'Date',
        reference: 'date',
        maxValue: new Date(),
        name: 'date'
    }],
    buttons: [{
        text: 'Submit',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        handler: 'onSubmitButtonClick'
    }]
});