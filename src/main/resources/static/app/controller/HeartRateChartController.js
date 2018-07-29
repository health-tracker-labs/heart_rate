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
        var button = toolBar.getView().getReferences().refreshButton;
        this.refreshChart();
        setTimeout(function () {
            button.enable();
        }, 1000 * 60 * 10);
    },

    privates: {
        refreshChart: function (args) {
            var chart = this.getView().getReferences().chart;
            if (!args) {
                chart.getStore().load();
            } else {
                chart.getStore().load(args)
            }
            chart.redraw();
        }
    }
});
