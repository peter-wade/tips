/**
 * 初始化详情对话框
 */
var CmsAdvInfoDlg = {
    cmsAdvInfoData : {}
};

/**
 * 清除数据
 */
CmsAdvInfoDlg.clearData = function() {
    this.cmsAdvInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CmsAdvInfoDlg.set = function(key, val) {
    this.cmsAdvInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CmsAdvInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CmsAdvInfoDlg.close = function() {
    parent.layer.close(window.parent.CmsAdv.layerIndex);
}

/**
 * 收集数据
 */
CmsAdvInfoDlg.collectData = function() {
    this
    .set('advId')
    .set('advName')
    .set('advCode')
    .set('startTime')
    .set('endTime')
    .set('advPosition')
    .set('urlType')
    .set('advUrl')
    .set('remark')
    .set('status')
    .set('dataState')
    .set('createBy')
    .set('createDate')
    .set('updateBy')
    .set('maxSize')
    .set('advSort')
    ;
}

/**
 * 提交添加
 */
CmsAdvInfoDlg.addSubmit = function() {
	
		 // 提交信息
		var formId = "addadv" ; 
	  
	    var formData = new FormData($("form")[0]);  
	    $.ajax({  
           url : Feng.ctxPath + "/cmsAdv/add",  
           data: formData,    
           type: "post",
           async: false,    
           cache: false,    
           contentType: false,    
           processData: false, 
           success : function(data) {  
        	   Feng.success("添加成功!");
               window.parent.CmsAdv.table.refresh();
               CmsAdvInfoDlg.close();
           },  
           error : function() {  
           	Feng.error("添加失败!");
           }
       });
	    
	
}

/**
 * 提交修改
 */
CmsAdvInfoDlg.editSubmit = function() {

	
	
	 // 提交信息
	var formId = "updateadv" ; 
  
    var formData = new FormData($("form")[0]);  
    $.ajax({  
       url : Feng.ctxPath + "/cmsAdv/update",  
       data: formData,    
       type: "post",
       async: false,    
       cache: false,    
       contentType: false,    
       processData: false, 
       success : function(data) {  
    	   Feng.success("更新成功!");
           window.parent.CmsAdv.table.refresh();
           CmsAdvInfoDlg.close();
       },  
       error : function() {  
       	Feng.error("更新失败!");
       }
   });
}

$(function() {
	
	var advCampCode = document.getElementById('advCampCode').value;
    
	 /**
	 * 获取活动编码
     */
	var options = {
		url:'/S100001/camplist',
		data: {},
		type:"get",
		async:true,
		success:function(data) {
            if (data && data.length > 0) {
                $("#addForm select[id=advCampCode] option").remove();
                $("#addForm select[id=advCampCode]").append("<option value=\"\">---请选择关联的活动编码---</option>");
            	for(var index = 0 ;index<data.length;index++){

            		var current = data[index];
                     if(advCampCode==current.campCode){
                    	 $("#addForm select[id=advCampCode]").append("<option "+"selected = \"selected\" "+" value=\""+current.campCode+"\">"+current.campName+"</option>");
                     }else{
                    	 $("#addForm select[id=advCampCode]").append("<option value=\""+current.campCode+"\">"+current.campName+"</option>");
                     }
            	}
       		}
		}
	} ;
	$.ajax(options);

	bindSearch();
});



var bindSearch = function () {
	var advCampCode = document.getElementById('advCampCode').value;
	 //活动广告绑定活动编码
    $("#addForm :input[name=advType]").bind("change",function() {
        if (this.value == 'campAdv') {
           $("[name='advCampCode']").removeAttr('disabled');
            $("#advFundCode").attr('disabled','disabled');
            $("#advUrl").attr('disabled','disabled');
            /**
			 * 获取活动编码
             */
			var options = {
				url:'/S100001/camplist',
				data: {},
				type:"get",
				async:true,
				success:function(data) {
                    if (data && data.length > 0) {
                        $("#addForm select[id=advCampCode] option").remove();
                        $("#addForm select[id=advCampCode]").append("<option value=\"\">---请选择关联的活动编码---</option>");
                    	for(var index = 0 ;index<data.length;index++){

                    		var current = data[index];
                    		  if(advCampCode==current.campCode){
                             	 $("#addForm select[id=advCampCode]").append("<option "+"selected = \"selected\" "+" value=\""+current.campCode+"\">"+current.campName+"</option>");
                              }else{
                             	 $("#addForm select[id=advCampCode]").append("<option value=\""+current.campCode+"\">"+current.campName+"</option>");
                              }
                    	}
               		}
				}
			} ;
			$.ajax(options);
        } else if(this.value == 'fundDetail'){
            $("[name='advCampCode']").attr('disabled','disabled');
            $("#advFundCode").removeAttr('disabled');
            $("#advUrl").attr('disabled','disabled');
        }else{
        	 $("[name='advCampCode']").attr('disabled','disabled');
             $("#advFundCode").attr('disabled','disabled');
             $("#advUrl").removeAttr('disabled');
        }
    });
};


