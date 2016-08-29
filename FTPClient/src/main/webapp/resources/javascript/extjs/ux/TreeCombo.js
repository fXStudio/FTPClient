Ext.define('Ext.ux.TreeCombo', {
	extend: 'Ext.form.field.Picker',
	alias: 'widget.treecombo',
	
	displayField: 'text',
	valueField: 'sn',
	initComponent: function() {
		var	me = this;
		
		me.tree = Ext.create('Ext.tree.Panel', {
			alias: 'widget.assetstree',
			
			height: 300,
			floating: true,
			useArrows: true,
			rootVisible: false,
			store: me.store,
			listeners: {
				load: function(targetObj, node, records) {
					me.setValue(records[0]);// 默认值
					this.expandAll();
				},
				itemclick: function(view, record) {
					me.setValue(record);// 选中值
					me.onTriggerClick();// 选中后关闭树菜单
				}
			}
		});
		me.callParent(arguments);
	},
	setValue: function(record) {
		if(record) {
			this.value = record.get(this.valueField);
			this.setRawValue(record.get(this.displayField));
		}
		return this;
	},
	getValue: function() {
		return this.value;
	},
	getSubmitValue: function() {
		return this.value;
	},
	createPicker: function() {
		return this.tree;
	}
});