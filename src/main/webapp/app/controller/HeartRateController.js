Ext.define('app.controller.HeartRateController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.HeartRateController',

    onSeriesTooltipRender: function (tooltip, record, item) {
        var title = Ext.String.format("upper: {0} / lower: {1}", record.data.upper, record.data.lower);
        tooltip.setHtml(title);
    },
});