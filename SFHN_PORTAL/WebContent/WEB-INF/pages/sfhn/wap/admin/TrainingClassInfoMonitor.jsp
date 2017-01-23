<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%
	String playurl = (String)request.getAttribute("playurl");
	Boolean isBroadcasting = (Boolean)request.getAttribute("isBroadcasting");
	TrainingClassInfoBean classInfo = (TrainingClassInfoBean)request.getAttribute("classInfoBean");
%>
<%@ include file="../../../include/header.jsp" %>

<!DOCTYPE html>
<!-- saved from url=(0033)http://yun.lpmas.com/admin/thread -->
<html class=""><!--<![endif]--><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="renderer" content="webkit">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta content="c0c6d687cf0f11fc3f67955a6bba6d69c211075e" name="csrf-token">
  <title>实时监控</title>
    
  <link href="<%=STATIC_URL %>css/bootstrap.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/common.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/other.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/admin.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/admin_v2.css" rel="stylesheet">
  <link rel="stylesheet" media="screen" href="<%=STATIC_URL %>css/es-icon.css">
  <script src="<%=STATIC_URL %>js/admin_common.js"></script>
  <script type="text/javascript" src="<%=STATIC_URL %>ckplayer/ckplayer.js" charset="utf-8"></script>
    <!--[if lt IE 9]>

  <![endif]-->

</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="Index.do">智慧湘农云管理中心</a>
      </div>
      <div class="navbar-collapse collapse">
        <ul class=" navbar-nav navbar-right">
          <li class="dropdown" style="margin-top: 15px;">
                <a href="javascript:;" style="text-decoration:none;cursor:default" class="dropdown-toggle"> <%=request.getAttribute("UserName") %></a>
              	<span>|</span>
              	<a href="http://passport.1haowenjian.cn/user/UserInfoManage.do" class="dropdown-toggle" data-toggle="dropdown">账号设置</a>
              	<span>|</span>
              	<a href="http://passport.1haowenjian.cn/user/Logout.do">退出</a>
           </li>
        </ul>
      </div><!--/.navbar-collapse -->
    </div>
  </div>
  <div class="container">
    <div class="row">
        <div >
          <div class="page-header clearfix" style="padding-bottom: 10px">
            <h1 class="pull-left">实时监控：<%=classInfo.getClassName() %> </h1>
            <div class="pull-right">
            </div>
          </div>
	  <div id="thread-table-container">
	     <%if(!isBroadcasting){ %>
	    	 该课程当前没有实时监控画面
	     <%} %>
	  </div>
      </div>
   </div>
  </div>
</body>
<%if(isBroadcasting){ %>
<script type="text/javascript">
	//如果你不需要某项设置，可以直接删除，注意var flashvars的最后一个值后面不能有逗号
	function loadedHandler(){
		if(CKobject.getObjectById('ckplayer_a1').getType()){//说明使用html5播放器
			console.log('播放器已加载，调用的是HTML5播放模块');
		}
		else{
			console.log('播放器已加载，调用的是Flash播放模块');
		}
	}
	var flashvars={
		//f:'rtmp://live.hkstv.hk.lxdns.com/live/hks',//视频地址http://210.59.248.53/hls-live/livepkgr/_definst_/liveevent/live-ch1-2.m3u8
		f:'<%=STATIC_URL %>ckplayer/m3u8.swf',//视频地址
		a:'<%=playurl%>',//调用时的参数，只有当s>0的时候有效
		s:'4',//调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
		c:'0',//是否读取文本配置,0不是，1是
		i:'',//初始图片地址
		d:'',//暂停时播放的广告，swf/图片,多个用竖线隔开，图片要加链接地址，没有的时候留空就行
		//l:'http://www.ckplayer.com/down/adv6.1_1.swf|http://www.ckplayer.com/down/adv6.1_2.swf',//前置广告，swf/图片/视频，多个用竖线隔开，图片和视频要加链接地址
		e:'8',//视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放并且不调用广告，3是调用视频推荐列表的插件，4是清除视频流并调用js功能和1差不多，5是暂停播放并且调用暂停广告
		v:'50',//默认音量，0-100之间
		p:'0',//视频默认0是暂停，1是播放，2是不加载视频
		h:'0',//播放http视频流时采用何种拖动方法，=0不使用任意拖动，=1是使用按关键帧，=2是按时间点，=3是自动判断按什么(如果视频格式是.mp4就按关键帧，.flv就按关键时间)，=4也是自动判断(只要包含字符mp4就按mp4来，只要包含字符flv就按flv来)
		lv:'1',//是否是直播流，=1则锁定进度栏
		loaded:'loadedHandler',//当播放器加载完成后发送该js函数loaded
		};
	var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always'};//这里定义播放器的其它参数如背景色（跟flashvars中的b不同），是否支持全屏，是否支持交互
	function addflash(){
		if(CKobject.Flash()['f']){
			CKobject._K_('thread-table-container').innerHTML='';
			CKobject.embedSWF('<%=STATIC_URL %>ckplayer/ckplayer.swf','thread-table-container','ckplayer_a1','600','400',flashvars,params);
		}
		else{
			alert('该环境中没有安装flash插件，无法切换');
		}
	}
	addflash();
</script>
<%} %>
</html>