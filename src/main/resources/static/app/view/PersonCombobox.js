Ext.define('app.view.PersonCombobox', {
	extend: 'Ext.form.ComboBox',

	xtype: 'personCombobox',

	requires: [
		'app.controller.PersonComboboxController'
	],

	controller: 'PersonComboboxController',

	store: Ext.create('app.store.PersonStore'),

	displayField: 'name',
	valueField: 'id',

	queryMode: 'local',
	editable: false,

	listeners: {
		render: 'onRender'
	}
})