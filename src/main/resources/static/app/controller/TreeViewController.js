Ext.define('app.controller.TreeViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.TreeViewController',

    onItemClick: function(oThis, record, item, index, e, eOpts) {
        var treeView = this.getView();
        var panel = treeView.up('panel');
        var content = panel.lookupReference('content');

        if (content.items.length > 0) {
            content.removeAll(false);
        }
        var grid;
        if (record.data.text == 'Users') {
            grid = Ext.create('app.view.UsersGrid');
        }
        if (grid) {
            content.add(grid);
        }
    }
});