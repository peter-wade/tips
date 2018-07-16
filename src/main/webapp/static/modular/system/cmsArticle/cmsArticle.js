/**
 * 管理初始化
 */
var CmsArticle = {
    id: "CmsArticleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CmsArticle.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'articleId', visible: false, align: 'center', valign: 'middle'},
/*            {
    			title : '资讯显示位置',
    			field : 'articleLocationShow',
    			visible : true,
   			    align : 'center',
    			valign : 'middle',
    			formatter : function(cellvalue) {
    				return AplusCodeToMsg('article_location_show',cellvalue);
    			}
    		},
*/            {
    			title : '资讯类型',
    			field : 'articleType',
    			visible : true,
    			align : 'center',
    			valign : 'middle',
    			formatter : function(cellvalue) {
    				return AplusCodeToMsg('article_type',cellvalue);
    			}
    		},
            {title: '资讯标题', field: 'articleTitle', visible: true, align: 'center', valign: 'middle'},
            {title: '所属时间', field: 'articleAcctTime', visible: true, align: 'center', valign: 'middle'},
            {title: '基金代码', field: 'fundCode', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'articleSort', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '发布时间', field: 'pubTime', visible: false, align: 'center', valign: 'middle'},
            {title: '审核通过时间', field: 'auditTime', visible: false, align: 'center', valign: 'middle'},
            {
    			title : '状态',
    			field : 'status',
    			visible : true,
    			align : 'center',
    			valign : 'middle',
    			formatter : function(cellvalue) {
    				return AplusCodeToMsg('article_status',cellvalue);
    			}
    		},
            {title: '作者', field: 'author', visible: false, align: 'center', valign: 'middle'},
            {title: '概要', field: 'summary', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'createBy', visible: false, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: false, align: 'center', valign: 'middle'},
            {title: '更新人', field: 'updateBy', visible: false, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: false, align: 'center', valign: 'middle'},
            {title: '内容', field: 'content', visible: false, align: 'center', valign: 'middle'},
            {title: '标签', field: 'tagsNames', visible: true, align: 'center', valign: 'middle'},
            {title: '分享次数', field: 'shareSize', visible: true, align: 'center', valign: 'middle'},
            {title: '阅读次数', field: 'readSize', visible: true, align: 'center', valign: 'middle'},
            {title: '点赞次数', field: 'thumbsUpSize', visible: true, align: 'center', valign: 'middle'},
            {title: '收藏次数', field: 'collectionSize', visible: true, align: 'center', valign: 'middle'}
        	
    ];
};

/**
 * 检查是否选中
 */

/**
 * 打开查看详情
 */
CmsArticle.openCmsArticleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cmsArticle/cmsArticle_update/' + CmsArticle.seItem.articleId
        });
        this.layerIndex = index;
    }
};

var id = 'ListTable';
//参数校验 是否选中
function check(){
	 var selected = $('#' + id).bootstrapTable('getSelections');
    if(selected.length == 0){
       Feng.info("请先选中表格中的某一记录！");
       return false;
    }else{
    	seItem = selected;
       return seItem;
    }
};

var IntfCampUserListImport = function(){  
	
	
	this.check=function(){
		 var selected = $('#' + id).bootstrapTable('getSelections');
	    if(selected.length == 0){
	       Feng.info("请先选中表格中的某一记录！");
	       return false;
	    }else{
	    	seItem = selected;
	       return seItem;
	    }
	};
    this.init = function(){       
        //模拟上传excel  
         $("#uploadEventBtn").unbind("click").bind("click",function(){  
             $("#uploadEventFile").click();  
         });  
         $("#uploadEventFile").bind("change",function(){  
             $("#uploadEventPath").attr("value",$("#uploadEventFile").val());  
         });  
    };  
    //点击上传按钮  
    this.uploadBtn = function(articleId){  
        var uploadEventFile = $("#uploadEventFile").val();  
        var number = $("#number").val();  
        if(uploadEventFile == ''){  
            Feng.error("请选择excel,再上传!");
        }else if(uploadEventFile.lastIndexOf(".xls")<0){//可判断以.xls和.xlsx结尾的excel  
            Feng.error("只能上传Excel文件!");
        }else if(number == ''){
        	Feng.error("请输入开始读取行数!");
        }else{  
			var seItem = this.check();
            var url = Feng.ctxPath + '/S100004/uploadart?articleId='+seItem[0].articleId;  
            var formData = new FormData($('form')[1]);  
            intfCampUserListImport.sendAjaxRequest(url,'POST',formData);  
        }  
    };  
    this.sendAjaxRequest = function(url,type,data){  
        $.ajax({  
            url : url,  
            type : type,  
            data : data,  
            success : function(result) {  
            	if(result.code == '200'){
            		Feng.success(result.msg);
            		// 刷新表数据
            		IntfCampUser.table.refresh();
            	}else{
            		Feng.error(result.msg);
            	}
            },  
            error : function() {  
                Feng.error("excel上传失败！");
            },  
            cache : false,  
            contentType : false,  
            processData : false  
        });  
    };  
} 
var intfCampUserListImport; 
$(function () {
	check();
    var defaultColunms = CmsArticle.initColumn();
    var options = {
            pageInitLoadData:true
    };
    AplusList.initTable(Feng.ctxPath+"/cmsArticle/list", defaultColunms,options);
    intfCampUserListImport = new IntfCampUserListImport();
    intfCampUserListImport.init();
});
