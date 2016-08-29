/*
 * Note that this control will most likely remain as an example, and not as a core Ext form
 * control.  However, the API will be changing in a future release and so should not yet be
 * treated as a final, stable API at this time.
 */

/**
 * A control that allows selection of between two Ext.ux.form.MultiSelect controls.
 */
Ext.define('Ext.ux.form.ItemSelector', {
    extend: 'Ext.ux.form.MultiSelect',
    alias: ['widget.itemselectorfield', 'widget.itemselector'],
    alternateClassName: ['Ext.ux.ItemSelector'],
    requires: ['Ext.button.Button', 'Ext.ux.form.MultiSelect'],

    /**
     * @cfg {Boolean} [hideNavIcons=false] True to hide the navigation icons
     */
    hideNavIcons:false,

    /**
     * @cfg {Array} buttons Defines the set of buttons that should be displayed in between the ItemSelector
     * fields. Defaults to <tt>['top', 'up', 'add', 'remove', 'down', 'bottom']</tt>. These names are used
     * to build the button CSS class names, and to look up the button text labels in {@link #buttonsText}.
     * This can be overridden with a custom Array to change which buttons are displayed or their order.
     */
    buttons: ['add', 'remove'],
    
    /**
     * Component Layout
     */
    layout: { type: 'hbox', align: 'stretch' },
    
    /**
     * Component Initialize Method
     */
    initComponent: function() {
        var me = this;
        
        // Invoke Parent Constructor
        me.callParent();

        // bindStore must be called after the fromField has been created because
        // it copies records from our configured Store into the fromField's Store
        me.bindStore(me.store);
    },
    
    /**
     * Create Item List
     */
    createList: function(title){
        var me = this;

        return Ext.create('Ext.ux.form.MultiSelect', {
            // We don't want the multiselects themselves to act like fields,
            // so override these methods to prevent them from including
            // any of their values
        	title: title,
            submitValue: false,
            getSubmitData: function(){ return null; },
            getModelData: function(){ return null; },
            flex: 1,
            store: { model: me.store.model, data: [] },
            displayField: me.displayField,
            valueField: me.valueField,
            disabled: me.disabled,
            listeners: {
                boundList: {
                    scope: me,
                    itemdblclick: me.onItemDblClick,
                    drop: me.syncValue
                }
            }
        });
    },
    
    /**
     * Layout Component
     */
    setupItems: function() {
        var me = this;
        
        // FromFiled
        me.fromField = me.createList(me.fromTitle);
        // ToFiled
        me.toField = me.createList(me.toTitle);
        
        // Component Layout
        return [
            me.fromField,
            {
                xtype: 'container',
                margins: '0 4',
                layout: {
                    type: 'vbox',
                    pack: 'center'
                },
                items: me.createButtons()
            },
            me.toField
        ];
    },
    /**
     * Create Nav Buttons
     */
    createButtons: function() {
        var me = this, buttons = [];

        if (!me.hideNavIcons) {
            Ext.Array.forEach(me.buttons, function(name) {
                buttons.push({
                    xtype: 'button',
                    handler: me['on' + Ext.String.capitalize(name) + 'BtnClick'],
                    cls: Ext.baseCSSPrefix + 'form-itemselector-btn',
                    iconCls: Ext.baseCSSPrefix + 'form-itemselector-' + name,
                    navBtn: true,
                    scope: me,
                    margin: '12 0 0 0'
                });
            });
        }
        return buttons;
    },

    /**
     * Get the selected records from the specified list.
     * 
     * Records will be returned *in store order*, not in order of selection.
     * @param {Ext.view.BoundList} list The list to read selections from.
     * @return {Ext.data.Model[]} The selected records in store order.
     * 
     */
    getSelections: function(list) {
        var store = list.getStore();

        return Ext.Array.sort(list.getSelectionModel().getSelection(), function(a, b) {
            a = store.indexOf(a);
            b = store.indexOf(b);

            if (a < b) { return -1; } 
            else if (a > b) { return 1; }
            return 0;
        });
    },
    
    /**
     * Add Button Click
     */
    onAddBtnClick : function() {
        var me = this,
            selected = me.getSelections(me.fromField.boundList);

        me.moveRec(false, me.toField.boundList.getStore().getRange());
        me.moveRec(true, selected);
        me.toField.boundList.getSelectionModel().select(selected);
    },
    /**
     * Remove Button Click
     */
    onRemoveBtnClick : function() {
        var me = this,
            selected = me.getSelections(me.toField.boundList);

        me.moveRec(false, selected);
        me.fromField.boundList.getSelectionModel().select(selected);
    },
    /**
     * Record Move
     */
    moveRec: function(add, recs) {
        var me = this,
            fromField = me.fromField,
            toField   = me.toField,
            fromStore = add ? fromField.store : toField.store,
            toStore   = add ? toField.store   : fromField.store;

        fromStore.suspendEvents();
        toStore.suspendEvents();
        fromStore.remove(recs);
        toStore.add(recs);
        fromStore.resumeEvents();
        toStore.resumeEvents();

        fromField.boundList.refresh();
        toField.boundList.refresh();

        me.syncValue();
    },

    /**
     * Sync Store 
     */
    syncValue: function() {
        var me = this; 
        me.mixins.field.setValue.call(me, me.setupValue(me.toField.store.getRange()));
    },
    
    /**
     * Item Double Click
     */
    onItemDblClick: function(view, rec) {
    	var isAdd = view === this.fromField.boundList;
    	if(isAdd) {this.moveRec(false, this.toField.boundList.getStore().getRange());}
        this.moveRec(isAdd, rec);
    },
    
    /**
     * Set Selected Value
     */
    setValue: function(value) {
        var me = this,
            fromField = me.fromField,
            toField = me.toField,
            fromStore = fromField.store,
            toStore = toField.store,
            selected;

        // Wait for from store to be loaded
        if (!me.fromStorePopulated) {
            me.fromField.store.on({
                load: Ext.Function.bind(me.setValue, me, [value]),
                single: true
            });
            return;
        }

        value = me.setupValue(value);
        me.mixins.field.setValue.call(me, value);

        selected = me.getRecordsForValue(value);

        // Clear both left and right Stores.
        // Both stores must not fire events during this process.
        fromStore.suspendEvents();
        toStore.suspendEvents();
        fromStore.removeAll();
        toStore.removeAll();

        // Reset fromStore
        me.populateFromStore(me.store);

        // Copy selection across to toStore
        Ext.Array.forEach(selected, function(rec){
            // In the from store, move it over
            if (fromStore.indexOf(rec) > -1) {
                fromStore.remove(rec);
            }
            toStore.add(rec);
        });

        // Stores may now fire events
        fromStore.resumeEvents();
        toStore.resumeEvents();

        // Refresh both sides and then update the app layout
        Ext.suspendLayouts();
        fromField.boundList.refresh();
        toField.boundList.refresh();
        Ext.resumeLayouts(true);        
    },
    
    /**
     * Bind Store
     */
    onBindStore: function(store, initial) {
        var me = this;

        if (me.fromField) {
            me.fromField.store.removeAll()
            me.toField.store.removeAll();

            // Add everything to the from field as soon as the Store is loaded
            if (store.getCount()) { me.populateFromStore(store); } 
            else { me.store.on('load', me.populateFromStore, me); }
        }
    },
    
    /**
     * Load Data From Store
     */
    populateFromStore: function(store) {
    	var me = this,
            fromStore = me.fromField.store,
            toStore = me.toField.store;
        
        // Flag set when the fromStore has been loaded
        me.fromStorePopulated = true;
        
        // Remove All Records Pre Loaded
        fromStore.removeAll();
        toStore.removeAll();
        
        // Add All To From Filed
        fromStore.add(store.getRange());
        
        // Add Record To Store
        Ext.Array.each(store.getRange(), function(record){
        	if(record.get("remark") === "true"){ 
        		me.moveRec(true, record)
        	} 
        });
        
        // setValue waits for the from Store to be loaded
        fromStore.fireEvent('load', fromStore);
    },
    onDestroy: function(){
        this.bindStore(null);
        this.callParent();
    },
    getValue: function(){
    	var record = this.toField.store.getAt(0);
    	return record ? record.get('sysid') : ""; 
    }
});
