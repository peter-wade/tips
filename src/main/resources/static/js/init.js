
var phoneNo= null;
var custFlag = null ;
$(document).ready(function(){
	custFlag  =GetQueryString('custFlag');
	phoneNo  =GetQueryString('phoneNo');
});

function GetQueryString(name) {
	   var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
	   var r = window.location.search.substr(1).match(reg);
	   if (r != null) return unescape(r[2]); return null;
}		


var getCommandList = function(url,articleId){
	
	$.ajax({
		type: 'POST',
 		url:  url +'S090030',
        data:  {reqData:'{reqData:{"articleId":"'+articleId+'","phoneNo":"'+phoneNo+'"}}'},
        cache: true,
        async: true,
        success: function(returnData,msg){
        	if(returnData.respCode =="0000"){
	        	var list =returnData.articleRespList;
	        	if (list.length >0) {
		        	for(var i =0 ; i<list.length;i++){
		        	$("#recommend").append(
		        			"<li>" +
		        			"<div class='main'>" +
		        			"<h5 id ='dtlarticleTitle_"+i+"'>"+list[i].articleTitle+"</h5>"+
		        			"	<img src='"+list[i].imageUrl+"' alt=''>"+
		        			"</div>"+
		        			"<div class='attr'>"+
		        			"	<span>"+list[i].pubTime+"</span>"+
		        			"	<span class='l'>"+
		        			"		<div>"+
		        			"			<img src='../images/read.png' style='width:23px;height:17px;'>"+
		        			"			<span>"+list[i].readSize+"</span>"+
		        			"		</div>"+
		        			"		<div>"+
		        			"			<img src='../images/click.png' style='width:23px;height:17px;'>"+
		        			"			<span>"+list[i].thumbsUpSize+"</span>"+
		        			"		</div>"+
		        			"	</span>"+
		        			"</div>" +
		        			"</li>");
		            }
	            }else{
	            	$("#recommend").append("<p>暂无推荐</p>")
	            }	
        	}
		}
	});
} ; 


var getArticleInfo = function(url,articleId){
	
	$.ajax({
		type: 'POST',
 		url: url +"S090031",
        data:  {reqData:'{reqData:{"articleId":"'+articleId+'"}}'},
        cache: true,
        async: true,
        success: function(returnData,msg){
        	if(returnData.respCode =="0000"){
        		var data =returnData.respData;
        		$("#articleTitle").text(data.articleTitle);
        		$("#pubTime").text(data.pubTime);
        		$("#readSize").text(data.readSize);
        	}
        }
	});
	
} ; 

var updateVisit = function(url,articleId){
	
	$.ajax({
		type: 'POST',
 		url: url +"S090029",
        data:  {reqData:'{reqData:{"articleId":"'+articleId+'","behaviorType":"2","phoneNo":"'+phoneNo+'","typeValue":"1","custFlag":"'+custFlag+'"}}'},
        cache: true,
        async: true,
        success: function(returnData,msg){
        
        }
	});
     	
	
} ; 


	

	