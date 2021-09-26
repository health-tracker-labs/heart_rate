Ext.define('app.state.AddState', {
	extend: 'app.state.BaseState',

	next: function(context) {
		this.nextState = (this.nextState ? this.nextState : Ext.create('app.state.LoadState'));
		this.callParent([context]);
	},	
	previous: function(context) {
		this.prevState = (this.prevState ? this.prevState : Ext.create('app.state.EditState'));
		this.callParent([context]);
	},

	getName: function() {
		return 'Add';
	}
})