Ext.define('app.controller.UserAdditionFormController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.UserAdditionFormController',

    onSubmitClick: function () {
        const me= this;
        const form = this.getView();

        if (this.validateForm(form)) {
            const references = form.getReferences();

            const roles = this.getRoles(form);
            const user= {
                'username': references.user.getValue(),
                'password': references.password.getValue(),
                'state': references.state.getValue(),
                'roleIds': roles
            };

            Ext.Ajax.request({
                url: '../users',
                method: 'POST',
                jsonData: user,
                success: function (response) {
                    me.fireEvent('onRefreshStore');
                    me.destroyFormAndEnableAddUserButton();
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
        getRoles: function (form) {
            const references = form.getReferences();
            return [references.role1, references.role2]
                .map(role => role.getValue())
                .filter(roleId => roleId != null);
        },
        validateForm: function (form) {
            const references = form.getReferences();
            if (!references.role1.getValue()) {
                Ext.Msg.alert('Error', 'enter role 1');
                return false;
            }
            return form.isValid();
        },
        destroyFormAndEnableAddUserButton: function () {
            this.fireEvent('onEnableButton');
            this.getView().destroy();
        }
    }
});