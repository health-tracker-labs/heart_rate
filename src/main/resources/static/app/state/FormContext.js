Ext.define('app.state.FormContext', {
	currentState: undefined,

	changeState: function(state) {
		var stateName = state.getName();

		var beforeEvent = Ext.String.format('onBefore{0}StateChanged', stateName);
		var afterEvent = Ext.String.format('onAfter{0}StateChanged', stateName);

		Ext.GlobalEvents.fireEvent(beforeEvent);
		this.currentState = state;
		Ext.GlobalEvents.fireEvent(afterEvent);
	},

	nextState: function() {
		this.currentState.next(this);
	},
	previousState: function() {
		this.currentState.previous(this);
	},
	cancel: function() {
		this.changeState(Ext.create('app.state.CancelState'));
		this.nextState();
	},

	constructor: function (config) {
		this.changeState(Ext.create('app.state.AddState')); 
	}
});