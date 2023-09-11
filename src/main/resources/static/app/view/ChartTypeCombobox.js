Ext.define('app.view.ChartTypeCombobox', {
	extend: 'Ext.form.ComboBox',

	xtype: 'chartTypeCombobox',

	requires: [
		'app.controller.ChartTypeComboboxController'
	],

	controller: 'ChartTypeComboboxController',

	store: Ext.create('app.store.ChartTypeStore'),

	displayField: 'name',
	valueField: 'id',

	queryMode: 'local',
	editable: false,

	listeners: {
		render: 'onRender',
		select: 'onSelectChartType'
	}
})