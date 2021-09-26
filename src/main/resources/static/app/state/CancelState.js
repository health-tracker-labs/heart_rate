Ext.define('app.state.CancelState', {
	extend: 'app.state.BaseState',

	next: function(context) {
		this.nextState = (this.nextState ? this.nextState : Ext.create('app.state.AddState'));
		this.callParent([context]);
	},	
	getName: function() {
		return 'Cancel';
	}
})