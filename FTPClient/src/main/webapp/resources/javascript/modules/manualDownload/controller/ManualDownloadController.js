Ext.define('ManualDownloadModule.controller.ManualDownloadController', {
	extend: 'Ext.app.Controller',
    
    // 对于代操作对象的引用
    refs: [
        {ref: 'manualdownloadform',      selector: 'form'}, 
        {ref: 'resetBtn',       selector: 'manualdownloadform button[action=reset]'}, 
        {ref: 'loginBtn',       selector: 'manualdownloadform button[action=login]'}, 
        {ref: 'beginDateField',  selector: 'manualdownloadform datefield[name=beginDate]'}, 
        {ref: 'endDateField',  selector: 'manualdownloadform datefield[name=endDate]'}
    ],
    
    // Cotroller的业务处理
    init: function() {
        this.control({
            'manualdownloadform button[action=reset]': {// 输入项重置
                click: function(){
                    this.getManualdownloadform().getForm().reset();
                }
            },
            'manualdownloadform button[action=login]': {// FTP下载事件
                click: function() {
                    var formObj = this.getManualdownloadform().getForm();// form表单对象 
                    var beginDateField = this.getBeginDateField();// 开始日期
                    var endDateField = this.getEndDateField();// 结束日期

                    // 检查表单项的录入是否存在问题
                    if (formObj.isValid()) {
                        // 提交表单
                        formObj.submit({
                            waitMsg: '数据量较大请耐心等待，指令提交后本窗口可关闭，指令会在后台执行。', // 提示信息  
                            waitTitle: '提示', // 标题  
                            url: 'services/manualdownload', // 请求的url地址  
                            method: 'POST', // 请求方式  
                            success: function(form, action) { // 添加数据成功后，重新加载数据源刷新表单 
                                Ext.Msg.alert('提示', '指令执行完成');   
                            },   
                            error: function() {
	                            Ext.Msg.alert('提示', '指令执行错误,更多信息请咨询管理员');   
                            }
                        });
                    }
                }
            }
        });
    }
});