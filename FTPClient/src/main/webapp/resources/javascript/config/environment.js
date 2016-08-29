/**
 * System Config
 */

// 开启ADM功能，关闭脚本缓存
Ext.Loader.setConfig({
    enabled: true,
    disableCaching: true,
    paths:{
    	'Ext.ux': 'javascript/extjs/ux/',
    	'Ext.plugins': 'javascript/plugins'
    }
});

// 开启Cookie记录功能，有效期为7日
Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
    expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7))
}));

// 初始化提示功能，设定提示位置为字段边缘
Ext.tip.QuickTipManager.init();
Ext.form.Field.prototype.msgTarget = 'side';

// 禁止backspace键引起的浏览器回退
new Ext.util.KeyMap({
    target: window.document,
    key: 8,
    fn: function(obj,e){
    	var type = e.target.type;// 组件类型
    	var readonly = e.target.readOnly;// 组件只读属性
    	
    	// 在非编辑区或只读对象上禁用回退键
    	if((type != 'text' && type != 'textarea' && type != 'password') || readonly){
    		e.stopEvent();
    	} 
    },
    scope: this
});

// 禁止整个页面的右键
Ext.getDoc().on("contextmenu", function(e) { e.stopEvent(); });