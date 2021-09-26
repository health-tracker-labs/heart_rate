Ext.define('app.state.BaseState', {
	nextState: undefined,
	prevState: undefined,

	next: function(context) {
		if (!this.nextState) {
			Ext.raise('Not implemented');
		}
		context.changeState(this.nextState);
	},	
	previous: function(context) {
		if (!this.prevState) {
			Ext.raise('Not implemented');
		}
		context.changeState(this.prevState);
	},

	getName: function() {
		Ext.raise('Not implemented');
	}
})