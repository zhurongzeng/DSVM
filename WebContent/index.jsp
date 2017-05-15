<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>  
<!-- header -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	//禁止客户端使用缓存 
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
  	<meta http-equiv="cache-control" content="no-cache">
  	<meta http-equiv="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="stylesheet" href="files/css/bootstrap.css">
    <link rel="stylesheet" href="files/css/animate.css">
    <link rel="stylesheet" href="files/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="files/css/dataTables.css">
    <link rel="stylesheet" href="files/css/app.css">
    <link rel="stylesheet" href="files/css/style.css">
    <link rel="stylesheet" href="files/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="files/css/compatibility.css">

    <!--[if IE]>
        <script src="files/js/indexOf.js"></script>
    <![endif]-->
    <!--[if IE 8]>
    	 <link rel="stylesheet" href="files/css/forIe8.css">
    <![endif]-->
  <script src="files/js/jquery.min.js"></script>
  <script src="files/js/bootstrap.js"></script>
  <script src="files/js/bootstrap-datetimepicker.js"></script>
  <script src="files/js/bootstrap-datetimepicker.zh-CN.js"></script>
  <script src="files/js/jquery.dataTables.js"></script>
  <script src="files/layer/layer.js"></script>
  <script src="files/js/comment.js"></script>
    <title>大数视频管理系统</title>
    <script type="text/javascript">

		
		function logofUser(path){
			layer.confirm('您确定要退出？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
			  window.location.href = path;
			}, function(){
			});
		}
		
		$(function(){
			$("#signVideo").click(function(){
				clickMenu("signVideoListView.do");
			});
			
			$("#userManagement").click(function(){
				clickMenu("queryUserListView.do");
			});
		});
		
		function clickMenu(path){
			if(path!=null && typeof(path) != "undefined"){
				$("#mainId").html("");
				layer.closeAll();
				$("#mainId").load(path);
			}
		}
    </script>
</head>
<!--end header -->

<body>
<div class="app">

    <!-- navbar -->
    <div class="app-header navbar">
        <!-- navbar header -->
        <div class="navbar-header bg-blue">
            <!-- brand -->
            <a href="#" class="navbar-brand text-lt">
                <i class="fa fa-btc"></i>
                <img src="files/img/logo.png" alt=".">

            </a>
            <!-- / brand -->
        </div>
        <!-- / navbar header -->

         <!-- navbar collapse -->
        <div class="collapse pos-rlt navbar-collapse box-shadow bg-white-only">
            <h4 class="plat-title">大数视频管理系统</h4>
            <!-- nabar right -->
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle clear" >
                        <span class="thumb-sm pull-left-xs m-t-n-sm m-b-n-sm m-l-sm">
                            <i class="icon-user icon-large"></i>
                         </span>
                        <span class="hidden-sm hidden-md">${session_user_info.userName}</span>
                    </a>
                    <a href="javascript:void(0);" onclick="logofUser('logofUser.do')">
                        <span class="thumb-sm pull-left-xs m-t-n-sm m-b-n-sm m-l-sm">
                            <i class="icon-share-alt icon-large"></i>
                         </span>
                        <sapn class="hidden-sm hidden-md">退出</sapn>
                    </a>
                </li>
            </ul>
            <!-- / navbar right -->

        </div>
        <!-- / navbar collapse -->
    </div>
    <!-- / navbar -->
    <!--end header -->


	<!-- menu -->
    <!-- menu -->
    <div class="app-aside hidden-xs bg-dark">
        <div class="aside-wrap">
            <div class="navi-wrap">

                <!-- nav -->
                <nav ui-nav class="navi">
                    <ul class="nav">
                    	<c:if test="${'super_admin' != session_user_info.roleInfoDTO.roleId }">
                        <li class="nav-list active">
                            <a href="" class="auto">
                          <span class="pull-right text-muted">
                            <i class="fa fa-fw icon-angle-right text"></i>
                            <i class="fa fa-fw icon-angle-down text-active"></i>
                          </span>
                                <i class="icon-edit icon font-bold"></i>
                                <span class="font-bold" >面签视频</span>
                            </a>
                            <ul class="nav nav-sub dk" style="">
                                <li><a  id="signVideo" href="javascript:void(0);" ><span>面签视频信息</span></a></li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${'head_admin' == session_user_info.roleInfoDTO.roleId || 'super_admin' == session_user_info.roleInfoDTO.roleId }">
                        
                        <li class="nav-list">
                            <a class="auto">
                          <span class="pull-right text-muted">
                            <i class="fa fa-fw icon-angle-right text"></i>
                            <i class="fa fa-fw icon-angle-down text-active"></i>
                          </span>
                                <i class=" icon-tint icon"></i>
                                <span class="font-bold" >系统管理</span>
                            </a>
                            <ul class="nav nav-sub dk">
                                <li><a id="userManagement" href="javascript:void(0);"  > <span>用户管理</span></a></li>
                            </ul>
                        </li>
                        </c:if>
                    </ul>
                </nav>
                <!-- nav -->
            </div>
        </div>
    </div>
    <!-- / menu -->
    <!--end menu -->

    <!-- main -->
    <div class="app-content" id="mainId">
       
        <jsp:include page="/mainlayout/main.jsp" flush="true"></jsp:include>
        
    </div>
    <!--end main -->

    <!-- foot -->
    <div class="app-footer wrapper b-t bg-light">
       	 版权所有 © 2016 深圳前海大数金融服务有限公司
    </div>
</div>
</body>
</html>
<!--end foot -->
