var historyUrl = "";
function logOut(){
	var url = "/user/logout"
	$.get("/api/1/capi/proxy?url=" + url, function(data) {
		$("#content").html(data.page);
	});
}
function openTab(tab,url) {
	$("#content").html("<div class='row' style='text-align:center;margin-top:30vh;'>"
			+"<div style='padding-bottom:20px;'><i class='fa fa-cog fa-spin fa-5x' style='color:#e0e1e2;'></i></div>"
				+"</div>");
	$("#back").click(function(){
		window.location.href= historyUrl;
	});
	$.get("/api/1/capi/proxy?url=" + url, function(data) {
		$("#content").html(data.page);
		$(".tabItem").removeClass("tabItem-active");
		$(".tabItem[tab="+tab+"]").addClass("tabItem-active");
	});
}
function viewLink(tab,url){
	$("#content").html("<div class='row' style='text-align:center;margin-top:30vh;'>"
			+"<div style='padding-bottom:20px;'><i class='fa fa-cog fa-spin fa-5x' style='color:#e0e1e2;'></i></div>"
				+"</div>");
	$("#back").click(function(){
		window.location.href= historyUrl;
	});
	url = encodeURI(url);
	$.get("/api/1/capi/viewfile?url=" + url, function(data) {
		$("#content").html(data.page);
		$(".tabItem").removeClass("tabItem-active");
		$(".tabItem[tab="+tab+"]").addClass("tabItem-active");
	});}
function openLink(url) {
	$.get("/api/1/capi/proxy?type=download&url=" + url.substring(1), function(data) {
		window.open(data.url);
	});
}
function search(url){
	openTab(2, url+"?q="+$("#q").val());
}
$(document).ready(function() {
	historyUrl = "/home/c/"+$("#url").val();
	var status = $("#status").val();
	//alert(status);
	
	if (status != 0) {
//		window.location.href = "/home/c/404.html";
		showMessageDialog("提示", "代码托管系统维护中,即将返回首页", function() {
			window.location.href = "/";
		});
	}
	$("#logout").click(function(){
		logOut();
	});
});
