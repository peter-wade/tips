
/**
 * 初始化通知详情对话框
 */
var CmsArticleInfoDlg = {
	CmsArticleInfoDlg: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
};


$(function () {
	bindSelectEvent();
	
    Feng.initValidator("noticeInfoForm", CmsArticleInfoDlg.validateFields);
});

var reloadHtmlContent = function(){
    if(UE.getEditor("articleContent").queryCommandState('source')!=0)//判断编辑模式状态:0表示【源代码】HTML视图；1是【设计】视图,即可见即所得；-1表示不可用
    {
        UE.getEditor("articleContent").execCommand('source'); //切换到【设计】视图
    }
};


var addSubmit = function(){
	
	var options = {
		url:'/S100003/add',
		type:"post",
		dataType:'json',
		success:function(data){
		}
	}
	$("#addForm").ajaxSubmit(options);
	
};

/**
 * 资讯预览
 */
var previewPage = function(){
    $("#addForm").attr("action","/S100003/preview");
    $("#addForm").attr("method","post");
    $("#addForm").attr("target","_bank");
    document.getElementById("addForm").submit();
};


var bindSelectEvent = function(){
	$("#addForm :input[name=articleSourceType]").bind("change",function(){
			if(this.value=='Y'){
				$("#articleUrl").removeAttr('disabled');
				sourceValid();
				setDisabled();
			}else if (this.value=='N'){
				$("#articleUrl").attr('disabled','disabled');
				setEnabled();
			}

	});

};

function setDisabled() {
    UE.getEditor('articleContent').setDisabled('fullscreen');
    disableBtn("articleContent");
}
function setEnabled() {
    UE.getEditor('articleContent').setEnabled();
    enableBtn();
}


function setinitTag(){
  var tags = $("#articletagsNames").val();
  if(tags){
	  var obj = tags.split(",");  
	  if (obj && obj.length > 0) {
		  var strhtml="";
		  	for(var index = 0 ;index<obj.length;index++){
		  		var current = obj[index];
		  		strhtml+='<li><div class=\"tag-editor-spacer\">&nbsp;</div><div class=\"tag-editor-tag\">'+current+'</div><div class=\"tag-editor-delete\"><i></i></div></li>';
		  	}
		  	$('.placeholder').remove();
		  	$(".tag-editor").append(strhtml);
	  	
	  }
  }
}

var sourceValid = function(){
	$("#addForm").bootstrapValidator("addField", "articleUrl", {
             validators: {
                 notEmpty: {
                     message: '资讯缩URL不能为空！'
                 }
             }
    });

};

//切换模板
var content = null ;
var templateArray ;
var switchTemplate = function(template){
    if(content){
        /**
         * 第一次进来的时候是需要初始化的，所以不需要提示。
         */
        layer.confirm("切换模板后将会重置编辑框内容，是否继续？", {
            btn: ['继续编辑', '算了，暂不更改']
        }, function (index) {
            /**
             * 第一次进来初始化内容
             */
            initContent(template);
            layer.close(index);
            return ;
        }, function (index) {
            //需要将下拉框值恢复
            $("#editorTemplateCode").val($("#editorTemplateCode").attr("oldValue"));
            layer.close(index);
            return ;
        });
    }else{
        initContent(template);
    }

} ;

var initContent = function(template){
    $("#editorTemplateCode").attr("oldValue",template);
    if(templateArray && templateArray[template] && templateArray[template].templatePath){
        if(content){
            UE.delEditor('articleContent');
            $("#articleContent").remove();
            $("#articleContentDiv").prepend('<textarea name="content" id="articleContent" style="width: 695px; height: 400px;"></textarea>');
        }
        content = UE.getEditor('articleContent', {
            iframeCssUrl: templateArray[template].templatePath
        });
    }else{
        content = UE.getEditor('articleContent');
    }

    content.ready(function () {//编辑器初始化完成再赋值
        if (templateArray && templateArray[template] && templateArray[template].content) {
            content.setContent(templateArray[template].content);  //赋值给UEditor
        } else {
            content.setContent("");
        }
    });
}


