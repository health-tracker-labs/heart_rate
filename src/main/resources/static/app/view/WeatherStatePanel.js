Ext.define('app.view.WeatherStatePanel', {
    extend: 'Ext.Panel',

    requires: [
        'app.controller.WeatherStatePanelController'
    ],

    layout: {
        type: 'table',
        columns: 2,
        tableAttrs: {
            style: {
                width: '100%',
                height: '100%'
            }
        },
        tdAttrs: {
            valign: 'middle'
        }
    },

    width: 120,
    height: 65,

    alias: 'widget.view.WeatherStatePanel',
    controller: 'WeatherStatePanelController',


    items: [{
        xtype: 'image',
        reference: 'weatherStateImg'
    }, {
        xtype: 'panel',
        style: 'text-align: top',
        layout: {
            type: 'table',
            tdAttrs: {
                valign: 'top'
            }
        },
        items: [{
            xtype: 'label',
            style: 'font-size: 25px;',
            reference: 'temperatureLabel'
        }, {
            xtype: 'label',
            html: '&deg;C'
        }]
    }, {
        colspan: 2,
        style: 'text-align: center; font-style: oblique',
        reference: 'weatherDescription'
    }],

    listeners: {
        //render: 'onRender'
    }
});