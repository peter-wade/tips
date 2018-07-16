	/**
	 * 活动用户列表初始化
	 */
	var IntfCampUser = {
			seItem : null, //选中的条目
			table : null,
			layerIndex : -1
	};
	
	/**
	 * 活动用户列表的列
	 */
	IntfCampUser.initColumn = function() {
		return [
	        {field: 'selectItem', radio: true},
	            {title: '文件编号', field: 'fileId', visible: false, align: 'center', valign: 'middle'},
	            {title: '文件名称', field: 'fileName', visible: true, align: 'center', valign: 'middle',
	            	formatter (cellvalue, row, index) {
	    				return "<a href=\"javascript:void(0);\" onclick=\"IntfCampUser.openUploadUserList('" + row.fileId + "','" + row.fileName + "')\">" + cellvalue + "</a>";
	            	}
	            },
	            {title: '文件类型', field: 'fileType', visible: true, align: 'center', valign: 'middle'},
	            {title: '上传时间', field: 'uploadTime', visible: true, align: 'center', valign: 'middle'}
	    ];
	};

	/**
	 * 下载导入模板
	 */	
	IntfCampUser.downLoadBtn = function excelOut(){
		    var url = Feng.ctxPath +"/S100004/excelDownLoad";  
		    url = encodeURI(url);
		    location.href = url;  
		};
	/**
	 * 打开查看上传列表
	 */
	IntfCampUser.openUploadUserList = function(fileId, fileName) {
		var index = layer.open({
			type : 2,
			title: '【'+fileName+'】导入详情',
			area : [ '850px', '420px' ], //宽高
			fix : false, //不固定
			maxmin : true,
			content : Feng.ctxPath + '/S100005/index/' + fileId
		});
		this.layerIndex = index;
	};

   var IntfCampUserListImport = function(){  
          
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
        this.uploadBtn = function(){  
            var uploadEventFile = $("#uploadEventFile").val();  
            var number = $("#number").val();  
            if(uploadEventFile == ''){  
                Feng.error("请选择excel,再上传!");
            }else if(uploadEventFile.lastIndexOf(".xls")<0){//可判断以.xls和.xlsx结尾的excel  
                Feng.error("只能上传Excel文件!");
            }else if(number == ''){
            	Feng.error("请输入开始读取行数!");
            }else{  
                var url = Feng.ctxPath + '/S100004/upload?number='+'2';  
                var formData = new FormData($('form')[0]);  
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
    $(function(){  
    	intfCampUserListImport = new IntfCampUserListImport();  
       	var defaultColunms = IntfCampUser.initColumn();
    	var options = {"pageInitLoadData":true};
        AplusList.initTable(Feng.ctxPath+"/S100006/list/article",defaultColunms,options);
        intfCampUserListImport.init();  
    });  
    