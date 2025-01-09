Ext.define('app.controller.PersonComboboxController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.PersonComboboxController',

	onRender: function() {
		var me = this;
		var view = this.getView();

	    var url = '../persons/admin';

		view.getStore().getProxy().setUrl(url);
		view.getStore().load();
	},
})