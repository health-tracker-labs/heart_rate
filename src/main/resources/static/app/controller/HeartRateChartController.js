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
                onRefresh: 'refresh'
            },
            'MainToolBarController': {
                onRefreshWithButton: 'refreshWithButton'
            }
        }
    },

    refresh: function (personId, from, to){
        var chart = this.getView().getReferences().chart;
        chart.getStore().load({
            params: {
                personId: personId,
                from: from,
                to: to
            }
        });
        chart.redraw();
    },

    refreshWithButton: function (button){
        var chart = this.getView().getReferences().chart;
        setTimeout(function () {
            button.enable()
        }, 1000 * 60 * 5);
        chart.getStore().load();
        chart.redraw();
    }
});
