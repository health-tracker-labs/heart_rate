Ext.define('app.controller.ChartTypeComboboxController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.ChartTypeComboboxController',

	onRender: function() {
		var me = this;
		var view = this.getView();
		view.getStore().load({
			callback: function(records, operation, success) {
				me.uploadChartPackages(records);
			}
		});
	},

	onSelectChartType: function(combo, record, eOpts) {
		var view = this.getView();
		var centerRegion = view.up('viewport').down('container[region=center]');

		var chartName = record.data.name;

		var widgetName = Ext.String.format('widget.view.{0}Chart', chartName);
		var chartPanel = this.chartTypeMap.get(widgetName);

		if (chartPanel === undefined) {
			chartPanel = this.chartTypeMap.add(widgetName, Ext.create(widgetName));
		}

		centerRegion.removeAll(false);
		centerRegion.add(chartPanel);

		chartPanel.fireEvent('onReloadChartStore');
	},

	privates: {
		chartTypeMap: Ext.util.HashMap.create(),

		uploadChartPackages: function(records) {
			var me = this;
			Ext.each(records, function(rec) {
				me.uploadChartPackage(rec);
			})
		},
		uploadChartPackage: function(rec) {
			Ext.require(Ext.String.format('app.view.{0}Chart', rec.data.name));
		}
	}
})