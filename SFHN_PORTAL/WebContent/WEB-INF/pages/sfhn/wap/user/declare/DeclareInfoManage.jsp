<%@page import="com.lpmas.sfhn.portal.declare.config.FormInstructionConfig"%>
<%@page import="com.lpmas.sfhn.portal.declare.config.PortalEntryConfig"%>
<%@page import="com.lpmas.sfhn.declare.config.DeclareInfoConfig"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.bean.StatusBean"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.constant.info.*"%>
<%@page import="com.fasterxml.jackson.core.type.*"%>
<%@page import="com.lpmas.sfhn.declare.bean.DeclareInfoBean"%>

<%
	DeclareInfoBean declareInfoBean = (DeclareInfoBean)request.getAttribute("DeclareInfoBean");
    Map<String, Boolean> moduleFinishedMap = (Map<String, Boolean>)request.getAttribute("ModuleFinishedMap");  
    int declareType = ParamKit.getIntParameter(request, "declareType", 0);
%>
<%@ include file="../../../../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
<link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
<title>在线申报系统</title>
</head>
<body class="article_bg">
    <div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>在线申报系统</div>
    <div class="placeHolder"></div>
	<section class="g-pb65">
	<%for(Entry<String, Boolean> entry : moduleFinishedMap.entrySet()){ %>
	<a href="<%=PORTAL_URL %><%=PortalEntryConfig.DECLARE_MODULE_URL_MAP.get(entry.getKey()) %>?declareType=<%=declareType%>">
	<div class="declare-box g-mt10">
		<p>
			<span class="add-icon">+</span><%=DeclareInfoConfig.DECLARE_MODULE_MAP.get(entry.getKey()) %>
			<%if(declareInfoBean.getDeclareId()>0){ %>
				<%if(entry.getValue()){ %>
				<span  class="tip-p done">已完成</span>
				<%}else{ %>
				<span class="tip-p nodone">请填写</span>
				<%} %>		
			<%}%>
		</p>
	</div>
	</a>
	<%}%>
	<a href="FormInstructionDisplay.do?declareType=<%=declareType%>">
        <div class="g-mt10 formdetail-box">
            <p><span class="bor-r detail-icon">i</span>填表说明:</p>
        </div>
    </a>
	</section>
	<section class="popup">
	    <div class="popup-content">
	        <img class="imgAttr" src="<%=STATIC_URL %>images/nodone.png">
	        <span id="disc">请信息填写完整</span>
	        <input type="hidden" id="back_flag" value="">
	    </div>
	</section>
<footer>

<div class="footer-r" style="width:100%">
    <input type="button" name="commit" id="commit" value="提交审核">
</div>
</footer>

</body>
<script>
$(document).ready(function() {
	
	$("#commit").click(
			function() {
				var declareType = '<%=declareType%>';
				var declareId = '<%=declareInfoBean.getDeclareId()%>';
				var url='<%=PORTAL_URL%>/sfhn/DeclareInfoManage.do?declareType='+declareType+'&declareId='+declareId;
				$.post(url,'',function(data){
					if(data.code=='1'||data.code=='200'){
						window.location.href="DeclarePortalIndex.do";
					}else{
						showTip("false",data.message);
					}
				});
		});
});
function  showTip(status,text){
    if(status == 'true')
    {
        $('.imgAttr').attr('src','<%=STATIC_URL %>images/done.png');
        $('.popup-content span').css('color','#5DAB2F');
        $('#back_flag').val('true');
    }
    else{
        $('.imgAttr').attr('src','<%=STATIC_URL %>images/nodone.png');
        $('.popup-content span').css('color','#F32E00');
        $('#back_flag').val('false');
    }
    $('.popup-content span').text(text);
    $('.popup').show();
};
$('.popup').on('click', function () {
    $(this).hide();
    if($('#back_flag').val()=='true'){
    	window.location.href="DeclarePortalIndex.do";
    }
});
$('.popup-content').on('click',function(){
    event.stopPropagation();
});
function toolBarBack(){
	window.location.href='DeclarePortalIndex.do';
}
</script>
</html>