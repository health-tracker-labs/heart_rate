Ext.define('app.view.UsersGrid', {
    extend: 'Ext.grid.Panel',

    renderTo: Ext.getBody(),

    requires: [
        'app.store.UserStore',
        'app.controller.UserGridController',
        'app.model.UserModel'
    ],

    plugins: {
        ptype: 'rowediting',
        clicksToEdit: 1,
        listeners: {
            edit: 'onUpdateClick'
        }
    },

    controller: 'UserGridController',

    selModel: 'rowmodel',

    store: Ext.create('app.store.UserStore'),
    columns: [{
        header: 'id',
        dataIndex: 'id'
    }, {
        header: 'username',
        dataIndex: 'username',
        editor: {
            // defaults to textfield if no xtype is supplied
            xtype: 'textfield',
            allowBlank: false
        }
    }, {
        header: 'password',
        dataIndex: 'password',
        flex: 1,
        editor: {
            allowBlank: false
        }
    },{
        xtype: 'checkcolumn',
        header: 'Active?',
        dataIndex: 'state',
        listeners: {
            beforecheckchange: function() {
                return false;
            }
        },
        width: 60,
        editor: {
            xtype: 'checkbox',
            cls: 'x-grid-checkheader-editor',
            inputValue: true,
            uncheckedValue: false
        }
    }],
    tbar: [{
        text: 'Add User',
        handler: 'onAddUserClick'
    }, {
        text: 'Remove User',
        handler: 'onRemoveUserClick'
    }],

    height: 200,
    width: 400
});