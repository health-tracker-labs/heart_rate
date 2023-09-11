Ext.define('app.view.WeightChart', {
    extend: 'Ext.Panel',

    requires: [
        'app.controller.WeightChartController'
    ],

    controller: 'WeightChartController',

    alias: 'widget.view.WeightChart',

	listeners : {
		onReloadChartStore : 'onReloadChartStore'
	},

    items: [{
        xtype: 'cartesian',
        reference: 'chart',
        width: '100%',
        height: '100%',
        store: Ext.create('app.store.WeightChartStore'),
        insetPadding: {
            top: 40,
            right: 40,
            bottom: 20,
            left: 20
        },
        legend: {
            docked: 'right'
        },
        sprites: [{
            type: 'text',
            text: 'Weight',
            fontSize: 22,
            width: 100,
            height: 30,
            x: 40, // the sprite x position
            y: 20  // the sprite y position
        }],
        axes: [{
            type: 'numeric',
            fields: ['weight'],
            title: ['weight'],
            position: 'left',
            grid: false
        }, {
            type: 'category',
            title: 'Date',
            fields: 'date',
            position: 'bottom',
            style: {
                textPadding: 0 // remove extra padding between labels to make sure no labels are skipped
            },
            grid: true,
            label: {
                rotate: {
                    degrees: -45
                }
            }
        }],
        series: [{
            type: 'line',
            xField: 'date',
            yField: 'weight',
            smooth: true,
            style: {
                lineWidth: 4
            },
            marker: {
                radius: 4
            },
            highlight: {
                fillStyle: '#000',
                radius: 5,
                lineWidth: 2,
                strokeStyle: '#fff'
            },
            tooltip: {
                trackMouse: true,
                renderer: 'onSeriesTooltipRender'
            }
        }]
    }]
});