Ext.define('app.view.TimeOfDayCombobox', {
	extend: 'Ext.form.ComboBox',

	xtype: 'timeOfDayCombobox',

	store: Ext.create('app.store.TimeOfDayStore'),

	displayField: 'label',
	valueField: 'value',

	queryMode: 'local',
	editable: false,
})