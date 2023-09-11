Ext.define('app.controller.WeightChartController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.WeightChartController',

	onSeriesTooltipRender: function(tooltip, record, item) {
		var title = Ext.String.format("weight {0} on date {1}", record.data.weight, record.data.date);
		tooltip.setHtml(title);
	},

	onReloadChartStore: function() {
		var chart = this.getView().getReferences().chart;
		chart.getStore().load();
		chart.redraw();
	}
})