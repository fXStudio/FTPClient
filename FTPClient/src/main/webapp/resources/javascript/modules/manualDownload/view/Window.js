Ext.define('ManualDownloadModule.view.Window', {
	extend: 'Ext.window.Window',
    alias: 'widget.manualdownloadWindow',
    
    requires: ['ManualDownloadModule.view.Form'],
    
    header: {
    	title: 'FTP客户端工具',
        iconCls: 'login'
    },
    closable: false,
    draggable: false,
    resizable: false,
    width: 400,
    height: 160,
    layout: 'fit',   
    items: {xtype: 'manualdownloadform'}
})