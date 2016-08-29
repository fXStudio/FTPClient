Ext.define('ManualDownloadModule.view.Form' ,{
    extend: 'Ext.panel.Panel',
    alias: 'widget.manualdownloadform',
    
    layout: 'fit',
    items: [
        {
            xtype:'form',
        	defaults: {
                labelWidth: 70,
                labelAlign: 'right',
                anchor: '99%',
                allowBlank: false
            },
        	items:[
        	    {
        	    	html: "<div style='margin-top:10px;'></div>"
        	    },   
				{
					fieldLabel: '开始时间',
					labelAlign: 'right',
				   	name: 'beginDate',
				    xtype: 'datefield',
				    format: 'Ymd',
				    editable: false,
				    value: new Date()
				},
				{
					fieldLabel: '结束时间',
					labelAlign: 'right',
				    name: 'endDate',
				    xtype: 'datefield',
				    format: 'Ymd',
				    editable: false,
				    value: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
				}
	       ],
	       buttons: [
              {text: '重置', action: 'reset'}, 
              {text: '执行', type: 'submit',action: 'login'}
           ]
        }
	 ]
});