<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.sfhn.declare.bean.*"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<!DOCTYPE html>
<%
	DeclareInfoBean declareInfoBean = (DeclareInfoBean)request.getAttribute("DeclareInfoBean");
	DeclareImageBean imageBean = (DeclareImageBean) request.getAttribute("DeclareImageBean");
%>
<%@ include file="../../../../include/header.jsp" %>
<html>
<head>
<script src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录入图片</title>
</head>
<body>
<form id="formData" name="formData" method="post" action="DeclareImageManage.do"  >
		<input type="hidden" id="declareId" name="declareId" value="<%=declareInfoBean.getDeclareId() %>"  />
		<input type="hidden" id="declareType" name="declareType" value="<%=ParamKit.getIntParameter(request, "declareType", 0) %>"/>
		<input type="hidden" id="userId" name="userId" value="<%=declareInfoBean.getUserId() %>"/>
		<input type="hidden" id="imageType" name="imageType" value="<%=imageBean.getImageType()%>"/>
		<input type="hidden" id="imagePath" name="imagePath" value="<%=imageBean.getImagePath()%>"/>
		<input type="hidden" id="imgData" name="imgData" value=""/>
		<input type="hidden" id="imgType" name="imgType" value=""/>
		<canvas id="canvas" style="display:none"></canvas>
		<input type="submit" value="上传" onclick="go()"  />&nbsp;&nbsp;<em>(图片大小不超过1M)</em>
</form>
<img id="show" src="" width="100" height="150">
<input type="file" id="file" name="file" onchange="checkSize()"/>
</body>
<script>
var canvas = null;
function checkSize(){
	 //验证文件大小，文件类型
    var file = $('#file')[0].files[0];
	var path = window.URL.createObjectURL(file);
	var scale = 0;
	canvas = $('#canvas')[0];
     var size = file.size;
    if(size>1024*1024*5){
    	alert("文件大小超过限制");
    	return;
    }
    var name = file.name;
    if(!(name.lastIndexOf('jpg')>0||name.lastIndexOf('jpeg')>0||name.lastIndexOf('png')>0)){
    	alert("图片类型错误，请上传JPG或者PNG图片");
    	return;
    }
    $('#imgType').val(file.type);
    
    if(size<=1024*1024){
    	scale = 1;
    }else{
    	scale = 0.5;
    }
    
    var image = new Image();
    var ctx = $('#canvas')[0].getContext('2d');
    image.onload = function() {
        if (image.width != canvas.width) {
            canvas.width = image.width;
        }
        if (image.height != canvas.height) {
            canvas.height = image.height;
        }
        ctx.clearRect(image,0, 0, canvas.width, canvas.height);
        ctx.drawImage(image, 0, 0);
        var newUrl = canvas.toDataURL(file.type,scale);
        $('#imgData').val(newUrl);
        $('#show').attr("src",newUrl);
        
        window.URL.revokeObjectURL(path);
    }
    image.src=path;
}
function go(){
	console.log(canvas.toDataURL(file.type,scale));
}
</script>
</html>