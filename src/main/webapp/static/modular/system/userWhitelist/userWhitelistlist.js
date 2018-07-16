/**
 * camp管理初始化
 */
var CampattendList = {
		seItem : null, //选中的条目
		table : null,
		layerIndex : -1
};

/**
 * 初始化表格的列
 */
CampattendList.initColumn = function() {
	return [
		{field: 'selectItem', radio: true},
		{title: '文章ID', field: 'articleId', visible: true, align: 'center', valign: 'middle'},
		 {title: '文章标题', field: 'articleTitle', visible: true, align: 'center', valign: 'middle',
        	formatter (cellvalue, row, index) {
				return "<a href=\"javascript:void(0);\" onclick=\"CampattendList.openArticleInfoDetail('" + row.articleId + "','" + row.articleTitle + "')\">" + cellvalue + "</a>";
        	}
        },
        {title: '手机号码', field: 'phoneNo', visible: true, align: 'center', valign: 'middle'},
        {title: '白黑名单', field: 'listType', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
	];
};

/**
 * 打开查看camp详情
 */
CampattendList.openArticleInfoDetail = function(articleId, articleTitle) {
	var index = layer.open({
		type : 2,
		title : "【" + articleTitle + "】详情",
		area : [ '850px', '420px' ], //宽高
		fix : false, //不固定
		maxmin : true,
		content : Feng.ctxPath + '/S100003/cmsArticle_datail/' + articleId
	});
	this.layerIndex = index;
};


$(function() {
	var fileId = $("#fileId").val();
	var defaultColunms = CampattendList.initColumn();
	var options = {"pageInitLoadData":true};
    AplusList.initTable(Feng.ctxPath+"/S100005/list/"+fileId,defaultColunms,options);
});