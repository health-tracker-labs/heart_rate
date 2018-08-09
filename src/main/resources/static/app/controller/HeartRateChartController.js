Ext.define('app.controller.HeartRateChartController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.HeartRateChartController',

    onSeriesTooltipRender: function (tooltip, record, item) {
        var title = Ext.String.format("upper: {0} / lower: {1} / beatsPerMinute: {2} / weatherPressure: {3}",
            record.data.upper, record.data.lower, record.data.beatsPerMinute, record.data.weatherPressure);
        tooltip.setHtml(title);
    },

    listen: {
        controller: {
            '*': {
                onReloadChartStore: 'reloadChartStore'
            },
            'MainToolBarController': {
                onReloadChartStoreAndDisableRefreshButton: 'reloadChartStoreAndDisableRefreshButton'
            }
        }
    },

    reloadChartStore: function (personId, from, to) {
        this.refreshChart({
            params: {
                personId: personId,
                from: from,
                to: to
            }
        });
    },

    reloadChartStoreAndDisableRefreshButton: function (toolBar) {
        var me = this;
        Ext.Ajax.request({
            url: 'http://localhost:8080/heart_rate/pressure/pull.do',
            method: 'POST',
            success: function (response) {
                var button = toolBar.getView().lookupReference('refreshButton');
                me.refreshChart();
                button.enable();
            },
            failure: function (response) {
                button.enable();
            }
        });
    },

    privates: {
        refreshChart: function (args) {
            var chart = this.getView().lookupReference('chart');
            if (!args) {
                chart.getStore().load();
            } else {
                chart.getStore().load(args)
            }
            chart.redraw();
        }
    }
});
