/**
 * 管理初始化
 */
var CmsAdv = {
    id: "CmsAdvTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CmsAdv.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'advId', visible: false, align: 'center', valign: 'middle'},
            {title: '广告名称', field: 'advName', visible: true, align: 'center', valign: 'middle'},
            {title: '广告编码', field: 'advCode', visible: false, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位置', field: 'advPosition', visible: true, align: 'center', valign: 'middle',formatter:function(cellValue){
                    return AplusCodeToMsg("advPosition",cellValue) ;
                }},
            {title: '内部地址，外部地址（带http）', field: 'urlType', visible: false, align: 'center', valign: 'middle'},
            {title: '地址', field: 'advUrl', visible: true, align: 'center', valign: 'middle'},
            {title: '备注说明', field: 'remark', visible: false, align: 'center', valign: 'middle'},
            {title: '广告状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter:function(cellValue){
                return AplusCodeToMsg("status",cellValue) ;
            }},
            {title: '更新人', field: 'updateBy', visible: false, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
            {title: '广告类型属性值', field: 'advTypeAttr', visible: true, align: 'center', valign: 'middle'},
            {
    			title : '广告类型',
    			field : 'advType',
    			visible : true,
    			align : 'center',
    			valign : 'middle',
    			formatter : function(cellvalue) {
    				return AplusCodeToMsg('advType',cellvalue);
    			}
    		},
            {title: '最大展示数量', field: 'maxSize', visible: false, align: 'center', valign: 'middle'},
            {title: '排序序号', field: 'advSort', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
//CmsAdv.check = function () {
//    var selected = $('#' + this.id).bootstrapTable('getSelections');
//    if(selected.length == 0){
//        Feng.info("请先选中表格中的某一记录！");
//        return false;
//    }else{
//        CmsAdv.seItem = selected[0];
//        return true;
//    }
//};

/**
 * 点击添加
 */
//CmsAdv.openAddCmsAdv = function () {
//    var index = layer.open({
//        type: 2,
//        title: '添加',
//        area: ['800px', '420px'], //宽高
//        fix: false, //不固定
//        maxmin: true,
//        content: Feng.ctxPath + '/cmsAdv/cmsAdv_add'
//    });
//    this.layerIndex = index;
//};

/**
 * 打开查看详情
 */
CmsAdv.openCmsAdvDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cmsAdv/cmsAdv_update/' + CmsAdv.seItem.advId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
//CmsAdv.delete = function () {
//    if (this.check()) {
//  
//        var ajax = new $ax(Feng.ctxPath + "/cmsAdv/delete", function (data) {
//            Feng.success("删除成功!");
//            CmsAdv.table.refresh();
//        }, function (data) {
//            Feng.error("删除失败!" + data.responseJSON.message + "!");
//        });
//        ajax.set("cmsAdvId",CmsAdv.seItem.advId);
//        ajax.start();
//        CmsAdv.search();
//   
//    }
//};

/**
 * 查询列表
 */
CmsAdv.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CmsAdv.table.refresh({query: queryData});
};


$(function () {
    var defaultColunms = CmsAdv.initColumn();
//    var table = new BSTable(CmsAdv.id, "/cmsAdv/list", defaultColunms);
//    table.setPaginationType("client");
//    CmsAdv.table = table.init();
    var options = {
            pageInitLoadData:true
        };
    AplusList.initTable(Feng.ctxPath+"/cmsAdv/list", defaultColunms,options);
});
