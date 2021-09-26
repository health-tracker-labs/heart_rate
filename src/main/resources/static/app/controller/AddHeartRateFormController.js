Ext.define('app.controller.AddHeartRateFormController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.AddHeartRateFormController',

    requires: [
        'app.state.FormContext'
    ],

    listen: {
    	global: {
    		onAfterAddStateChanged: 'afterAddStateChanged',
    		onBeforeEditStateChanged: 'beforeEditStateChanged',
    		onAfterLoadStateChanged: 'afterLoadStateChanged',
    		onAfterEditStateChanged: 'afterEditStateChanged'
    	}
    },

    onPreviousButtonClick: function() {
    	this.previous();
    },
    onNextButtonClick: function() {
    	this.next();
    },

    afterAddStateChanged: function() {
    	var refs = this.getReferences();
    	refs.nextButton.setText('Edit');

    	refs.nextButton.show();
    	refs.previousButton.hide();

		refs.dateDataCombobox.hide();
    	this.showMainControls(true);

    	refs.addButton.enable();
    	refs.deleteButton.disable();
    	refs.cancelButton.disable();
    },

    afterLoadStateChanged: function() {
    	var refs = this.getReferences();

		refs.dateDataCombobox.hide();
    	this.showMainControls(false);
    	refs.personCombobox.show();
    	refs.dateCombobox.show();

    	refs.addButton.disable();
    	refs.deleteButton.disable();
    	refs.cancelButton.enable();
    },

    beforeEditStateChanged: function() {
    	this.loadDateDataCombobox();
    },
    afterEditStateChanged: function() {
    	var refs = this.getReferences();

		refs.dateDataCombobox.show();
    	this.showMainControls(true);
    	
    	refs.addButton.disable();
    	refs.deleteButton.enable();
    	refs.cancelButton.enable();
    },
    
    onCancelButtonClick: function () {
    	this.cancel();
    },

    onDateDataComboboxChange: function (element, newValue, oldValue) {
    	var id = newValue;
    	var refs = this.getReferences();

    	Ext.Ajax.request({
			url: '../heartRate/findById.json',
			method: 'GET',
			params: {
                id: id
            },
			success: function(response, opts) {
			    var obj = Ext.decode(response.responseText);
			    
			    refs.lowerPressureNumberField.setValue(obj.lowerPressure);
			    refs.upperPressureNumberField.setValue(obj.upperPressure);
			    refs.beatsPerMinuteNumberField.setValue(obj.beatsPerMinute);
			    refs.personCombobox.setValue(obj.person.id);
			    refs.dateCombobox.setValue(new Date(obj.date));

			    console.dir(obj);
			},
			
			failure: function(response, opts) {
			    console.log('server-side failure with status code ' + response.status);
			}
    	});
    },

    onDeleteButtonClick: function () {
    	var me = this;

    	var refs = this.getReferences();
    	var id = refs.dateDataCombobox.getValue();

    	Ext.Msg.confirm('Confirmation', 'Are you sure you want to delete the selected heart rate?', function (btnText) {
            if (btnText === 'yes') {
                Ext.Ajax.request({
                    url: '../heartRate/delete.do',
                    method: 'GET',
                    params: {
                        id: id
                    },
                    success: function (response) {
                        Ext.Msg.alert('Success', 'Delete success');

                        me.fireEvent('onReloadChartStore');
                        
                        me.loadDateDataCombobox();
                        me.formReset();
                    },
                    failure: function (response) {
                    	Ext.Msg.alert('Failed', 'Delete failed');
                    }
                });
            }
        }, this);
    },

    onSubmitButtonClick: function () {
        var me = this;
        var form = me.getView();
        if (form.isValid()) {
            form.submit({
                success: function (form, action) {
                    Ext.Msg.alert('Success', 'Save success');
                    me.fireEvent('onReloadChartStore');
                    form.reset();
                },
                failure: function (form, action) {
                    Ext.Msg.alert('Failed', 'Save failed');
                }
            });
        }
    },

    privates: {
    	loadDateDataCombobox: function() {
        	var refs = this.getReferences();

        	var personCombobox = refs.personCombobox;
        	var dateCombobox = refs.dateCombobox;
        	var dateDataCombobox = refs.dateDataCombobox;
        	var dateValue = Ext.Date.format(dateCombobox.getValue(), dateCombobox.submitFormat);

        	dateDataCombobox.getStore().load({
        		params: {
    	    		personId: personCombobox.getValue(),
    	        	from: dateValue,
    	    		to: dateValue
        		}
        	});
    	},
    	next: function() {
        	this.displayMoveButton();
    		this.getView().formContext.nextState();
        	this.formReset();
    	},
    	previous: function() {
        	this.displayMoveButton();
    		this.getView().formContext.previousState();
        	this.formReset();
    	},
    	cancel: function() {
    		this.getView().formContext.cancel();
        	this.formReset();
    	},

    	getReferences: function() {
        	return this.getView().getReferences();
    	},
    	formReset: function() {
        	this.getView().reset();
    	},

    	displayMoveButton: function() {
    		var refs = this.getReferences();

    		refs.nextButton.setText('>');
	    	refs.previousButton.setText('<');

	    	refs.nextButton.show();
	    	refs.previousButton.show();
    	},
    	showMainControls: function(isShow) {
    		var refs = this.getReferences();
    		var elements = [refs.upperPressureNumberField, 
    			refs.lowerPressureNumberField,
    			refs.beatsPerMinuteNumberField,
    			refs.personCombobox,
    			refs.dateCombobox
    		];
    		elements.forEach(function(element) {
    			isShow ? element.show() : element.hide();
    		});
    	}
    }
});