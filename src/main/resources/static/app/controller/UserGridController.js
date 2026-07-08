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
        const me= this;
        const sm = this.getView().getSelectionModel();

        const selectedRow = sm.getSelection()[0];
        if (!selectedRow) {
            Ext.Msg.alert('Failed', 'Please select a user to delete');
            return;
        }

        const id = selectedRow.data.id;
        const roles = selectedRow.data.roles;

        if (this.isAdmin(roles)) {
            Ext.Msg.alert('Failed', 'You can\'t delete admin');
            return;
        }

        Ext.Msg.confirm("Confirmation", "Do you want to delete user?", function (btnText) {
            if (btnText === "yes") {
                Ext.Ajax.request({
                    url: `../users/delete/${id}`,
                    method: 'DELETE',
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
        const store = this.getView().store;
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