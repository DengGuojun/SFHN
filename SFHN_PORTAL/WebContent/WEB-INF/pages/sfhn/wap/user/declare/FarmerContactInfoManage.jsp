<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.FarmerContactInfoBean" %>
<% 
    FarmerContactInfoBean bean = (FarmerContactInfoBean)request.getAttribute("FarmerContactInfoBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
%>
<%@ include file="../../../../include/header.jsp" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta charset="UTF-8" >
    <title>联系信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
    <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js" ></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/main.js"></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/plugin.js"></script>
    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/my.css">
    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/common.css">
</head>
<body >
	<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>联系信息</div>
	<div class="placeHolder"></div>
	  <input type="hidden" name="declareId" id="declareId" value="<%=bean.getDeclareId()%>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>"/>
	  <input type="hidden" name="declareType" id="declareType" value="<%=declareType%>"/>
	   <input type="hidden" name="country" id="country" value="中国">
	  <div class="g-pb65 show-1">
	    <section class="info-table">
	     <ul>
	      <li>
	      <dd><i class="star">*</i>手机号码:</dd>
	      <dt><input maxlength="20" placeholder="手机号码" type="number" name="userMobile" id="userMobile" value="<%=bean.getUserMobile() %>" /></dt>
	      </li>
           <li>
	      <dd><i class='nostart'></i>电子邮箱：</dd>
	      <dt><input maxlength="25" placeholder="电子邮箱" type="text" name="userEmail" id="userEmail" value="<%=bean.getUserEmail() %>"/></dt>
	      </li>
           <li>
	      <dd><i class='nostart'></i>QQ号：</dd>
	      <dt><input maxlength="20" placeholder="QQ号" type="number" name="userQq" id="userQq" value="<%=bean.getUserQq() %>" /></dt>
	      </li>
           <li>
	      <dd><i class='nostart'></i>微信号：</dd>
	      <dt><input maxlength="20" placeholder="微信号" type="text" name="userWechat" id="userWechat" value="<%=bean.getUserWechat() %>" /></dt>
	      </li>
	     <li class="address_add_pca clear">
                <label class="col-2 left" for="newAddLocation"><i class="star">*</i>户籍所在地：</label>
                <div class="col-10 cell" style="width:180px">
                    <input maxlength="25" type="hidden" name="province" id="province" autocomplete="off" data-verify="city" value="<%=bean.getProvince()%>">
                    <input maxlength="25"  type="hidden" name="city" id="city" autocomplete="off" data-verify="city" value="<%=bean.getCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="region" autocomplete="off" data-verify="city" value="<%=bean.getRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text"  name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=bean.getProvince()%><%=bean.getCity()%><%=bean.getRegion()%>">
                    </div>
                </div>
                <!-- <i class="col-2 cell"><b>*</b></i>-->
            </li>
            <li class="pca_select choice_address" id="pcaSelect">
                <p class="edit_title align_center">
                    <button type="button" class="cancel left"></button>
                    <b class="selected" data-content="pcaSelectProvince">省/直辖市</b>
                    <b data-content="pcaSelectCity">市</b>
                    <b data-content="pcaSelectRegion">区/县</b>
                </p>
                <div id="pcaSelectProvince"></div>
                <div id="pcaSelectCity"></div>
                <div id="pcaSelectRegion"></div>
            </li>
           <li>
	      <dd><i class="star">*</i>通讯地址：</dd>
	      <dt><input maxlength="100" placeholder="通讯地址" type="text" name="address" id="address" value="<%=bean.getAddress() %>" /></dt>
	      </li>
	    </ul>
    </section>
   </div>
	<footer class="footer-all" id="contact-save-btn">保存</footer>
   <footer class="footer-50">
    <div class="footer-w50l">取消</div>
    <div class="footer-w50r">保存</div></footer>
</body>
<script>
$('#contact-save-btn').on('click',function(){
        var declareType = $('#declareType').val();
        var status = $('#status').val();
        var declareId = $('#declareId').val();
        var userMobile = $('#userMobile').val();
        var userEmail = $('#userEmail').val();
        var userQq = $('#userQq').val();
        var userWechat = $('#userWechat').val();
        var familyPerson = $('#familyPerson').val();
        var province = $('#province').val();
        var city = $('#city').val();
        var region = $('#region').val();
        var address = $('#address').val();
        if (userMobile == ''){
			alert("手机必须填写");
			return;
		}
        if (province == '' || city == '' || region == ''){
			alert("户籍所在地必须填写");
			return;
		}
        if (address == ''){
			alert("通讯地址必须填写");
			return;
		}
        $.ajax({
            url: 'FarmerContactInfoManage.do',
            data: {
                declareType: declareType,
                status: status,
                declareId: declareId,
                userMobile: userMobile,
                userEmail: userEmail,
                userQq: userQq,
                userWechat: userWechat,
                familyPerson: familyPerson,
                province: province,
                city: city,
                region: region,
                address: address
            },
            type: 'POST',
            success: function (json) {
                console.log(json);
                json = ajaxAuthCheck(json);
                if (json.code == '1') {
                    window.location = 'DeclareInfoManage.do?declareType='+declareType
                }
                else if (json.code == '0') {
                    alert(json.message);
                }
            }
        })
});

(function(){
    te$.address.initPca('addressDetail', 'pcaSelect');


    $(".addr_choose").on("click tap",function(){
        var target = $(this);
        var id = target.data("id");
        if(target.hasClass("addr_close")){
            $.ajax({
                url:"/user/UserAddressDefaultJson.do?addressId=" + id,
                type:"GET",
                error:function(msg){
                    alert(msg)
                },
                success:function(result){
                    if(result.code == "1"){
                        $(".addr_open").removeClass("addr_open").addClass("addr_close");
                        target.removeClass("addr_close").addClass("addr_open");

                    }
                }
            })
        }

    })
})();
function toolBarBack(){
	window.location.href='DeclareInfoManage.do?declareType=<%=declareType%>';
}

</script>
</html>