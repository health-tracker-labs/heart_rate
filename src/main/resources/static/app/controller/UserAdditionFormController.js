Ext.define('app.controller.UserAdditionFormController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.UserAdditionFormController',

    onSubmitClick: function () {
        var form = this.getView();

        if (this.validateForm(form)) {
            var roles = this.formRolesArray(form);
            var user = Ext.create('UserModel', {
                'id': 0,
                'username': form.getReferences().user.getValue(),
                'password': form.getReferences().password.getValue(),
                'state': form.getReferences().state.getValue(),
                'roles': roles
            });

            Ext.Ajax.request({
                url: '../user/save',
                method: 'POST',
                jsonData: user.data,
                success: function (response) {
                    this.fireEvent('onRefreshStore');
                    this.destroyFormAndEnableAddUserButton();
                },
                failure: function (response) {
                }
            });
        }
    },

    onCloseClick: function () {
        this.destroyFormAndEnableAddUserButton();
    },

    privates: {
        formRolesArray: function (form) {
            var role1 = this.extractRole(form.getReferences().role1);

            if (role2) {
                var role2 = this.extractRole(form.getReferences().role2);
                return [role1, role2];
            }

            return [role1];
        },
        extractRole: function (combobox) {
            var r1 = combobox.getValue();
            var index = combobox.getStore().findExact('id', r1);
            return combobox.getStore().getAt(index).data;
        },
        validateForm: function (form) {
            if (!form.getReferences().role1.getValue()) {
                form.isValid();
                Ext.Msg.alert('Error', 'enter role 1');
                return false;
            }

            if (form.isValid()) {
                return true;
            }
        },
        destroyFormAndEnableAddUserButton: function () {
            this.fireEvent('onEnableButton');
            this.getView().destroy();
        }
    }
});