Ext.define('app.store.ChartTypeStore', {
	extend: 'Ext.data.Store',
	model: 'app.model.ChartTypeModel',

	proxy: {
		type: 'ajax',
		url: '../chartTypes',
		reader: {
			type: 'json',
			rootProperty: 'data'
		}
	},
	autoLoad: true
});