<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>  
<%
	//禁止客户端使用缓存 
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		//jquery的加载方法
		$(function(){
			onloadData();
			$("#table").dataTable(options);
		});
		
		function onloadData(){
			$.ajax({
					url : 'queryUserList.do',
					async : false,
					data : {
					},
					type : 'post',
					dataType : 'json',
					success : function(returnData) {
						if(returnData.scs){
							var list = eval(returnData.userList);
							for(var i=0;i<list.length;i++){
								var item = list[i];
								var partnerName = "";
								var roleName = "";
								if(typeof(item.partnerName) == "undefined"){
									partnerName = "";
								}else{
									partnerName = item.partnerName;
								}
								
								if(item.roleInfoDTO == null || typeof(item.roleInfoDTO) == "undefined"){
									roleName = "";
								}else{
									roleName = item.roleInfoDTO.roleName;
								}
								
								$("tbody").append("<tr>"+
								    		"<td>"+item.userId+"</td>"+
								    		"<td>"+item.userName+"</td>"+
								    		"<td>"+partnerName+"</td>"+
								    		"<td>"+roleName+"</td>"+
								    		"<td><a href='' class='modify-btn'>修改</a> &nbsp;| &nbsp;<a href='' class='delete-btn'>删除</a></td>"+
								"</tr>");
							}
						}else{
							layer.msg(returnData.msg,{icon:1});
						} 
					}
				});
		}
		
		 $(document).on('click','.delete-btn',function(){		 	
            var btn = $(this);
            layer.confirm("确定删除该信息吗？",{
                btn:['Y','N']
            },function(){
               var deleteData=[];
               btn.parents('tr').find('td').each(function(){
                	deleteData.push($(this).text());
            	});
            	if(deleteData!=null && deleteData.length > 0){
            		var userId =deleteData[0];
            		 $.ajax({
						url : 'deleteUserInfo.do',
						async : false,
						data : {
							userId : userId
						},
						type : 'post',
						dataType : 'json',
						success : function(returnData) {
							if(returnData.scs){
								btn.parents('tr').remove();
                				layer.alert(returnData.msg,{icon:1});
							}else{
								layer.alert(returnData.msg, {icon: 2});
							} 
						}
					});
            	}
            	
            });
            return false;
        });
        
        //添加与修改
        var data=[], onDom;
        $(document).on('click','.modify-btn,#addBtn',function(){
        	window.parent.layer.closeAll();
            if($(this).hasClass('modify-btn')){
                onDom = $(this);
            }
            data=[];
            $(this).parents('tr').find('td:not(:last)').each(function(){
                data.push($(this).text());
            });
            
            layer.open({
                type: 2,
                title: '新建与更新用户',
                shadeClose: true, //点击遮罩关闭层
                area : ['700px' , '430px'],
                content: ['addUserInfoView.do?inputDate='+data]
                
            });
            return false;
        });
	</script>
  
  <div class="widget-box">
            <div class="widget-title"> <span class="icon"><i class="icon icon-th"></i></span>
                <h5>用户信息列表</h5>
            </div>
            <div class="widget-content">
            	<div class="operation">
                    <a href="" id="addBtn" class="btn btn-info btn-sm l-s-1x">
                        <i class="icon icon-plus"></i> 新建用户</a>
                </div>
               
                <table class="table data-table" id="table">
                    <thead>
                    <tr>
                        <th>用户ID</th>
                        <th>用户姓名</th>
                        <th>合作方</th>
                        <th>角色</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

