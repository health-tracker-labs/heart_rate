Ext.define('app.model.ChartTypeModel', {
	extend: 'Ext.data.Model',
	alias: 'app.model.ChartTypeModel',

	fields: [
		{ name: 'id', type: 'string' },
		{ name: 'name', type: 'string' },
		{ name: 'description', type: 'string' }
	]
});