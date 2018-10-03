Ext.define('app.controller.UserGridController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.UserGridController',

    listen: {
        controller: {
            'UserAdditionFormController': {
                onRefreshStore: 'refreshStore',
                onEnableButton: 'enableButton'
            }
        }
    },

    onRemoveUserClick: function () {
        var me = this;
        var sm = this.getView().getSelectionModel();
        var id = sm.getSelection()[0].data.id;
        var roles = sm.getSelection()[0].data.roles;

        if (this.isAdmin(roles)) {
            Ext.Msg.alert('Failed', 'You can\'t delete admin');
            return;
        }

        Ext.Msg.confirm("Confirmation", "Do you want to delete user?", function (btnText) {
            if (btnText === "yes") {
                Ext.Ajax.request({
                    url: '../user/deleteUser',
                    method: 'POST',
                    params: {
                        id: id
                    },
                    success: function (response) {
                        me.refreshStore();
                    },
                    failure: function (response) {
                    }
                });
            }
        }, this);
    },

    onUpdateClick: function () {
        var store = this.getView().store;
        store.sync({
            success: function () {
                store.commitChanges();
            },
            failure: function () {
                store.rejectChanges();
            }
        });
    },

    onAddUserClick: function () {
        this.getView().lookupReference('addUserButton').disable();
        Ext.create('app.view.UserAdditionForm');
    },

    privates: {
        refreshStore: function () {
            this.getView().store.load();
        },
        enableButton: function () {
            this.getView().lookupReference('addUserButton').enable();
        },
        isAdmin: function (roles) {
            for (var i = 0; i < roles.length; i++) {
                if (roles[i].name == "ADMIN") {
                    return true;
                }
            }
            return false;
        }
    }
});