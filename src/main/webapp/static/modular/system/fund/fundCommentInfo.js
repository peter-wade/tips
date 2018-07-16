/**
 * 管理初始化
 */
var FundCommentInfo = {
    id: "FundCommentInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FundCommentInfo.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '组合代码', field: 'portfolioCode', visible: true, align: 'center', valign: 'middle'},
        {title: '组合名称', field: 'portfolioName', visible: true, align: 'center', valign: 'middle'},
        {title: '评论代码', field: 'commentsCode', visible: true, align: 'center', valign: 'middle'},
        {title: '客户昵称', field: 'custShortName', visible: true, align: 'center', valign: 'middle'},
        {title: '评论时间', field: 'commentsTime', visible: true, align: 'center', valign: 'middle'},
        {title: '评论内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '审核状态', field: 'reviewStatus', visible: true, align: 'center', valign: 'middle',
            formatter : function(cellvalue, row, index) {
            	if(cellvalue == "1"){
            		return "<span style=\"color:green\">"+AplusCodeToMsg('review_status',cellvalue)+"</span>";
            	}else{
            		return "<span style=\"color:gray\">"+AplusCodeToMsg('review_status',cellvalue)+"</span>";
            	}
            }
        }
    ];
};
 
/**
 * 检查是否选中
 */
FundCommentInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CmsAdv.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = FundCommentInfo.initColumn();
    AplusList.initTable(Feng.ctxPath+"/S100002/list",defaultColunms);
});
