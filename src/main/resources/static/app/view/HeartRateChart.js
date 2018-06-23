Ext.define('app.view.HeartRateChart', {
    extend: 'Ext.Panel',

    requires: [
        'app.controller.HeartRateChartController',
        'app.view.MainToolBar'
    ],

    controller: 'HeartRateChartController',

    width: 1440,
    renderTo: Ext.getBody(),


    items: [
        {
            xtype: 'view.myToolbar'

        },
        {
            xtype: 'cartesian',
            itemId: 'chart',
            width: '100%',
            height: 540,
            store: Ext.create('app.store.HeartRateChartStore'),
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
                text: 'Heart Pressure',
                fontSize: 22,
                width: 100,
                height: 30,
                x: 40, // the sprite x position
                y: 20  // the sprite y position
            }],
            axes: [{
                type: 'numeric',
                fields: ['upper', 'lower', 'beatsPerMinute'],
                position: 'left',
                grid: true
            }, {
                type: 'numeric',
                fields: ['weatherPressure'],
                position: 'right',
                grid: true
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
                yField: 'upper',
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
            }, {
                type: 'line',
                xField: 'date',
                yField: 'lower',
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
            }, {
                type: 'line',
                xField: 'date',
                yField: 'beatsPerMinute',
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
            }, {
                type: 'line',
                xField: 'date',
                yField: 'weatherPressure',
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