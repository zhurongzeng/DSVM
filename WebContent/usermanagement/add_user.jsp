<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//禁止客户端使用缓存 
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="pragma" content="no-cache">
  	<meta http-equiv="cache-control" content="no-cache">
  	<meta http-equiv="expires" content="0">
    <!--[if IE 8]>
    <link rel="stylesheet" href="files/css/compatibility_createData.css">
    <![endif]-->
    <link rel="stylesheet" href="files/css/bootstrap.css">
    <link rel="stylesheet" href="files/css/animate.css">
    <link rel="stylesheet" href="files/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="files/css/dataTables.css">
    <link rel="stylesheet" href="files/css/app.css">
    <link rel="stylesheet" href="files/css/style.css">

    <script src="js/jquery-1.11.2.min.js" type="text/javascript"></script>
    <script src="js/sha1.js" type="text/javascript"></script>
    <script src="files/layer/layer.js"></script>
    <title>后台管理</title>
    
	<script type="text/javascript">
	
		$(function(){
			loadData();
			$("#submit").click(function(){
				var userId = $("#userId").val();
	           	var userName = $("#userName").val();
	           	var userPwd = $("#userPwd").val();
	           	var partner = $("#partner").val();
	           	var flag = $("#flag").val();
	           	var role = $("#role").val();
	           	
				if(checkData(userId,userName,userPwd)){
					userPwd = hex_sha1(userPwd);
					var url = "";
					if(flag!=null && "add" == flag){
						url = "addUserInfo.do";
					}else{
						url = "updateUserInfo.do";
					}
					$.ajax({
						url : url,
						async : false,
						data : {
							user_id : userId,
							user_name : userName,
							user_pwd : userPwd,
							partner : partner,
							role_id    : role 
						},
						type : 'post',
						dataType : 'json',
						success : function(returnData) {
							if(returnData.scs){
								window.parent.$("#mainId").load("queryUserListView.do");
								window.parent.layer.closeAll();
							}else{
								layer.alert(returnData.msg, {icon: 2});
							} 
						}
					});
				}
				
           		
			});
		});
	
		function loadData(){
			$("#userId").val('${userId}');
			$("#userName").val('${userName}');
			var partnerName = "${partnerName}";
			var roleName = "${roleName}";
			var userId = $("#userId").val();
			if(userId!=null && userId!=""){
				$("#userId").attr("readOnly","readOnly");
			}
			$.ajax({
				url : 'queryPartnerTable.do',
				async : false,
				data : {
				},
				type : 'post',
				dataType : 'json',
				success : function(returnData) {
					if(returnData.scs){
						var list = eval(returnData.partnerList);
						for(var i=0;i<list.length;i++){
							var item = list[i];
							if(partnerName == item.partnerName){
								$("#partner").append("<option value='"+item.lineId+"'selected>"+item.partnerName+"</option>");
							}else{
								$("#partner").append("<option value='"+item.lineId+"'>"+item.partnerName+"</option>");
							}
							
						}
					}else{
						layer.alert(returnData.msg, {icon: 2});
					} 
				}
			});
			
			
			$.ajax({
				url : 'queryRoleInfo.do',
				async : false,
				data : {
				},
				type : 'post',
				dataType : 'json',
				success : function(returnData) {
					if(returnData.scs){
						var list = eval(returnData.roleList);
						for(var i=0;i<list.length;i++){
							var item = list[i];
							if(roleName == item.roleName){
								$("#role").append("<option value='"+item.roleId+"'selected>"+item.roleName+"</option>");
							}else{
								$("#role").append("<option value='"+item.roleId+"'>"+item.roleName+"</option>");
							}
							
						}
					}else{
						layer.alert(returnData.msg, {icon: 2});
					} 
				}
			});
			
		}
		
		function checkData(userId,userName,userPwd){

           	if(null == userId || "" == userId){
           		layer.alert("请输入用户ID！", {icon: 2});
           		return false;
           	}
           	
           	var userRegx=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,50}$/;
           	if(!userRegx.test(userId)){
           	 	layer.alert("用户ID长度4位字符起，用户ID必须由数字和英文组成，请重新输入！", {icon: 2});
           		return false;
           	 }
           		
           	if(null == userName || "" == userName){
           		layer.alert("请输入姓名！", {icon: 2});
           		return false;
           	}
           	
           	if(null == userPwd || "" == userPwd){
           		layer.alert("请输入密码！", {icon: 2});
           		return false;
           	}
           	
           	 var regx=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;
           	 if(!regx.test(userPwd)){
           	 	layer.alert("密码长度8-16位，密码必须由数字和英文组成，请重新输入！", {icon: 2});
           		return false;
           	 }
           	
           	return true;
		}
		
		
		
	</script>
</head>
<!--end header -->

<body>
<div class="alternate">
    <div class="form-horizontal">
    	<input type="hidden" id="flag" value="${flag}">
        <div class="input-group">
            <label class="control-label form-l"><span>用户ID</span>：</label>
            <div class="form-r">
                <input type="text" id="userId" maxlength="50" name="userId" class="form-control" placeholder="请输入用户ID">
            </div>
        </div>
        <div class="input-group">
            <label class="control-label form-l"><span>用户姓名</span>：</label>
            <div class="form-r">
                <input type="text" id="userName" maxlength="100" name="userName" class="form-control" placeholder="请输入用户姓名">
            </div>
        </div>

        <div class="input-group">
            <label class="control-label form-l"><span>用户密码</span>：</label>
            <div class="form-r">
                <input type="password" id="userPwd" maxlength="100" name="userPwd" class="form-control" placeholder="请输入用户密码">
            </div>
        </div>
        <div class="input-group">
            <label class="control-label form-l"><span>合作方</span>：</label>
            <div class="form-r">
                <select name="partner" id="partner" class="type">
                </select>
            </div>
        </div>
        <div class="input-group">
            <label class="control-label form-l"><span>角色</span>：</label>
            <div class="form-r">
                <select name="role" id="role" class="type">
                </select>
            </div>
        </div>
        <div class="input-group">
            <p class="notice text-danger"><i class="icon-remove-sign"></i> <span></span></p>
            <button id="submit" class="btn btn-info btn-sm" >提交</button>
        </div>
    </div>
</div>

</body>
</html>