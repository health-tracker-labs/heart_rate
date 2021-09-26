Ext.define('app.view.AddHeartRateForm', {
    extend: 'Ext.form.Panel',

    requires : [
        'app.controller.AddHeartRateFormController'
    ],

    controller: 'AddHeartRateFormController',

    formContext: undefined,

    width: 1440,
    title: 'Add Heart Rate',
    alias: 'widget.view.AddHeartRateForm',

    url: '../heartRate/save.do',

    defaults: {
        anchor: '100%',
        allowBlank: false
    },

    defaultType: 'numberfield',
    items: [{
        xtype: 'combobox',
        reference: 'dateDataCombobox',
        fieldLabel: 'Record Selector',
        store: Ext.create('app.store.DateDataStore'),
        queryMode: 'local',
        displayField: 'date',
        valueField: 'id',
        name: 'id',
        hidden: true,
        editable: false,
        allowBlank: true,
        listeners: {
        	change: 'onDateDataComboboxChange'
        }
    }, {
        fieldLabel: 'Upper',
        reference: 'upperPressureNumberField',
        name: 'upperPressure'
    }, {
        fieldLabel: 'Lower',
        reference: 'lowerPressureNumberField',
        name: 'lowerPressure'
    }, {
        fieldLabel: 'BPM',
        reference: 'beatsPerMinuteNumberField',
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
        reference: 'dateCombobox',
        anchor: '100%',
        fieldLabel: 'Date',
        format: 'm/d/Y',
        submitFormat: 'm/d/Y h:i:s',
        maxValue: new Date(),
        name: 'date'
    }],
    buttons: [{
        text: 'Save',
        reference: 'addButton',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        handler: 'onSubmitButtonClick'
    }, '|', {
        text: '<',
        reference: 'previousButton',
        handler: 'onPreviousButtonClick'
    }, {
        text: '>',
        reference: 'nextButton',
        handler: 'onNextButtonClick'
    }, {
        text: 'Delete',
        reference: 'deleteButton',
        handler: 'onDeleteButtonClick'
    }, {
    	text: 'Cancel',
        reference: 'cancelButton',
    	handler: 'onCancelButtonClick'
    }],
    
    initComponent: function() {
    	this.callParent(arguments);
    	this.formContext = Ext.create('app.state.FormContext');
    }
});