<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
	//禁止客户端使用缓存 
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
	
	<script type="text/javascript">
		$(function(){
			onloadData("1");
		});
	
		function DateDiff(sDate1, sDate2) { //sDate1和sDate2是yyyy-MM-dd格式
			var aDate, oDate1, oDate2, iDays;
			aDate = sDate1.split("-");
			oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); //转换为yyyy-MM-dd格式
			aDate = sDate2.split("-");
			oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
			iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
			return iDays; //返回相差天数
		}

		function onloadData(flag) {
			$("#table").DataTable(options).destroy();

			if(flag == "1") {
				var date = new Date(); 
				date.setDate(date.getDate() - 1);
				var y = date.getFullYear(); 
				var m = date.getMonth() + 1;
				var d = date.getDate(); 
				$("#startDate").val(y + "-" + m + "-" + d);
				$("#endDate").val(y + "-" + m + "-" + d);
			} 
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var clientName = $("#clientName").val();
			var idNo = $("#idNo").val();
			var partnerId = "${session_user_info.partnerId }";

			if (null == startDate || "" == startDate) {
				layer.alert("开始日期不能为空!", {icon : 2});
				return;
			}

			if (null == endDate || "" == endDate) {
				layer.alert("结束日期不能为空!", {icon : 2});
				return;
			}

			var start = new Date(startDate.replace("-", "/").replace("-", "/"));
			var end = new Date(endDate.replace("-", "/").replace("-", "/"));
			if (end < start) {
				layer.alert("结束日期不能小于开始日期!", {icon : 2});
				return;
			}

			var num =DateDiff(startDate, endDate);
			if(num>=15){
				layer.alert("只能查询15天内的数据,请重新选择日期!", {icon : 2});
				return;
			}

			$.ajax({
				url : 'querySignVideoList.do',
				async : false,
				data : {
					lineId : partnerId,
					clientName : clientName,
					idNo : idNo,
					startDate : startDate,
					endDate : endDate
				},
				type : 'post',
				dataType : 'json',
				success : function(returnData) {
					if(returnData.scs){
						if (returnData.resultList != null && returnData.resultList.length > 0) {
							$("#videoList").html("");
							var list = eval(returnData.resultList);
							for ( var i = 0; i < list.length; i++) {
								var item = list[i];
								var videoPath = item.cosPath;
								$("#videoList").append(
													"<tr>" 
														+ "<td>"
														+ "<input type='checkbox' onclick='checkRecord(this)'>"
														+ "</td>"
														+ "<td>"
														+ item.sSerialNo
														+ "<input type='hidden' id='videoPath"+i+"' value='"+videoPath+"' >"
														+ "<input type='hidden' id='serialNo"+i+"' value='"+item.sSerialNo+"' >"
														+ "<input type='hidden' id='customerName"+i+"' value='"+item.sCustomerName+"' >"
														+ "<input type='hidden' id='certId"+i+"' value='"+item.sCertId+"' >"
														+ "<input type='hidden' id='partnerName"+i+"' value='"+item.sPartnerName+"' >"
														+ "<input type='hidden' id='inputOrgId"+i+"' value='"+item.sInputOrgId+"' >"
														+ "<input type='hidden' id='orgName"+i+"' value='"+item.sOrgName+"' >"
														+ "<input type='hidden' id='signuserid"+i+"' value='"+item.sSignuserid+"' >"
														+ "<input type='hidden' id='signUserName"+i+"' value='"+item.sSignUserName+"' >"
														+ "<input type='hidden' id='videoStartDate"+i+"' value='"+item.sStartDate+"' >"
														+ "<input type='hidden' id='signAddressName"+i+"' value='"+item.sSignAddressName+"' >"
														+ "</td>"
														+ "<td>" + item.sCustomerName + "</td>"
														+ "<td>" + item.sCertId + "</td>"
														+ "<td>" + item.sPartnerName + "</td>"
														+ "<td>" + item.sOrgName + "</td>"
														+ "<td>" + item.sSignAddressName + "</td>"
														+ "<td>" + item.sSignUserName + "</td>"
														+ "<td><a href='javascript:void(0)' onclick='uploadVideo(" + i + ")'>下载</a>&nbsp;&nbsp;&nbsp;"
														+ "|&nbsp;&nbsp;<a href='javascript:void(0)' onclick='onlineVideo(" + i + ")'>在线视频</a></td>"
														+ "</tr>");
							}
						}else{
							$("#videoList").html("");
						}
					}else{
						layer.alert(returnData.msg, {icon : 2});
					}
					
				}
			});
			$("#table").DataTable(options);
		}

		function onlineVideo(count) {
			var serialNo = $("#serialNo" + count).val();
			var customerName = $("#customerName" + count).val();
			var certId = $("#certId" + count).val();
			var partnerName = $("#partnerName" + count).val();
			var inputOrgId = $("#inputOrgId" + count).val();
			var orgName = $("#orgName" + count).val();
			var signuserid = $("#signuserid" + count).val();
			var signUserName = $("#signUserName" + count).val();
			var videoStartDate = $("#videoStartDate" + count).val();
			var signAddressName = $("#signAddressName" + count).val();
			var cosPath = $("#videoPath" + count).val();

			var url = "onlineVideo.do?cosPath=" + cosPath;
			$.ajax({
				url : 'insertVideoOperationRecord.do',
				async : false,
				data : {
					cosPath : cosPath,
					serialNo : serialNo,
					customerName : customerName,
					certId : certId,
					partnerName : partnerName,
					inputOrgId : inputOrgId,
					orgName : orgName,
					signuserid : signuserid,
					signUserName : signUserName,
					startDate : videoStartDate,
					signAddressName : signAddressName,
					flag : '2'
				},
				type : 'post',
				dataType : 'json',
				success : function(returnData) {
					if (returnData.scs) {
						//window.open(url,"newwindow","width=750px,height=650px;resizable=no;scrollbars=no;status:yes;maximize:no;help:no;");
						layer.open({
				                type: 2,
				                title: '在线视频',
				                shadeClose: false, //点击遮罩关闭层
				                area : ['750px' , '650px'],
				                content: [url]
				                
				            });
					} else {
						layer.alert(returnData.msg, {
							icon : 2
						});
					}
				}
			});
		}

		function uploadVideo(count) {
			if (confirm("本视频内容涉及到客户及银行信息保密，请勿外泄及传播，是否确定下载吗？")) {
				var serialNo = $("#serialNo" + count).val();
				var customerName = $("#customerName" + count).val();
				var certId = $("#certId" + count).val();
				var partnerName = $("#partnerName" + count).val();
				var inputOrgId = $("#inputOrgId" + count).val();
				var orgName = $("#orgName" + count).val();
				var signuserid = $("#signuserid" + count).val();
				var signUserName = $("#signUserName" + count).val();
				var videoStartDate = $("#videoStartDate" + count).val();
				var signAddressName = $("#signAddressName" + count).val();
				var cosPath = $("#videoPath" + count).val();
				
				$.ajax({
					url : 'insertVideoOperationRecord.do',
					async : false,
					data : {
						cosPath : cosPath,
						serialNo : serialNo,
						customerName : customerName,
						certId : certId,
						partnerName : partnerName,
						inputOrgId : inputOrgId,
						orgName : orgName,
						signuserid : signuserid,
						signUserName : signUserName,
						startDate : videoStartDate,
						signAddressName : signAddressName,
						flag : '1'
					},
					type : 'post',
					dataType : 'json',
					success : function(returnData) {
						if (returnData.scs) {
							//location.href = cosPath;
							layer.open({
						                type: 2,
						                title: '下载视频',
						                shadeClose: false, //点击遮罩关闭层
						                area : ['550px' , '450px'],
						                content: [cosPath],
						                success: function(layero, index){
						                	debugger;
						                    var frame = $(layero).children()[1].children[0];
						                    console.info(frame);
						                  }
						            });
						} else {
							layer.alert(returnData.msg, {
								icon : 2
							});
						}
					}
				});
			}
		}
		
		function checkRecord(check){
			var item = $(check).parent().parent().find("td").eq(1).children();
			var serialNo = item.eq(1).val();
			if(check.checked){
				$("#recordTable").append(
						"<tr id='"+serialNo+"'>" 
						+ "<td>" + item.eq(0).val() + "</td>"
						+ "<td>" + item.eq(1).val() + "</td>"
						+ "<td>" + item.eq(2).val() + "</td>"
						+ "<td>" + item.eq(3).val() + "</td>"
						+ "<td>" + item.eq(4).val() + "</td>"
						+ "<td>" + item.eq(5).val() + "</td>"
						+ "<td>" + item.eq(6).val() + "</td>"
						+ "<td>" + item.eq(7).val() + "</td>"
						+ "<td>" + item.eq(8).val() + "</td>"
						+ "<td>" + item.eq(9).val() + "</td>"
						+ "<td>" + item.eq(10).val() + "</td>"
						+ "</tr>"
				);
			} else {
				$("#recordTable tr[id="+serialNo+"]").remove();
			}
		}
		
		function batchDownload() {
			var rows = $("#recordTable tr");
			if(rows.length <= 0) {
				layer.alert("请先选择一条记录！", {icon:2});
				return;
			} else if (rows.length > 10) {
				layer.alert("每次最多选择10条记录！", {icon:2});
				return;
			}
			if (confirm("本视频内容涉及到客户及银行信息保密，请勿外泄及传播，是否确定下载吗？")) {
				var videoRecords = [];
				$.each(rows, function(rowIndex, row) {
					  var videoInfo = {};
					  videoInfo.cosPath = $(row).children().eq(0).text();
					  videoInfo.serialNo = $(row).children().eq(1).text();
					  videoInfo.customerName = $(row).children().eq(2).text();
					  videoInfo.certId = $(row).children().eq(3).text();
					  videoInfo.partnerName = $(row).children().eq(4).text();
					  videoInfo.inputOrgId = $(row).children().eq(5).text();
					  videoInfo.orgName = $(row).children().eq(6).text();
					  videoInfo.signuserid = $(row).children().eq(7).text();
					  videoInfo.signUserName = $(row).children().eq(8).text();
					  videoInfo.startDate = $(row).children().eq(9).text();
					  videoInfo.signAddressName = $(row).children().eq(10).text();
					  videoInfo.flag = '1';
					  
					  videoRecords.push(videoInfo);
				});
				$.ajax({
					url : 'batchInsertVideoOperationRecord.do',
					contentType: 'application/json',
					type : 'post',
					dataType : 'json',
					async : false,
					data : JSON.stringify(videoRecords),
					success : function(returnData) {
						if(returnData.length > 0){
							for(var i = 0; i < returnData.length; i++){
								var result = JSON.parse(returnData[i]);
								if (result.scs) {
									layer.open({
						                type: 2,
						                title: '下载视频 - '+result.customerName,
						                shade: 0, 
						                area : ['550px' , '450px'],
						                content: [result.path]
						            });
								} else {
									layer.alert("客户["+result.customerName+"]"+returnData.msg, {icon:2});
								}
							}
						} else {
							layer.alert("无可下载视频！", {icon:2});
						}
					}
				});
			}
		}

		//时间选择器
		$('#startTime,#endTime').datetimepicker({
			language : 'zh-CN',
			format : 'yyyy-mm-dd',
			startDate : '2016-04-01',
			weekStart : 1, //一周从星期一开始。0（星期日）到6（星期六）
			todayBtn : 'linked',
			autoclose : 1,//当选择一个日期之后是否立即关闭此日期时间选择器。
			todayHighlight : true, //高亮显示今天的时间
			keyboardNavigation : true,
			startView : 2, //日期时间选择器打开之后首先显示的视图。0 or 'hour' for the hour view;1 or 'day' for the day view;2 or 'month' for month view (the default);3 or 'year' for the 12-month overview;4 or 'decade' for the 10-year overview.
			forceParse : 0,
			minView : 2
		});
	</script>
  
	<div class="widget-box">
	    <div class="widget-title"> <span class="icon"><i class="icon icon-th"></i></span>
	        <h5>面签视频信息</h5>
	    </div>
	    <div class="widget-content">
	    	<div class="input-group">
	     		客户姓名：<input type="text" id="clientName" maxlength="100" name="clientName" >
	        	身份证号码：<input type="text" id="idNo" maxlength="18" name="idNo" >
	  		</div>
	  		<div class="btn-group" role="group">
	            <a href="javascript:void(0)" onclick="onloadData('2');" class="btn btn-info btn-sm l-s-1x" id="coverBtn">确定</a>
	            <a href="javascript:void(0)" onclick="batchDownload();" class="btn btn-info btn-sm l-s-1x" id="downloadBtn" style="margin-left:10px;">下载</a>
	        </div>
	        <div class="input-append date" id="endTime" data-date="" data-date-format="dd-mm-yyyy">
	            	结束日期:<input class="span2" id="endDate" size="16" type="text" placeholder="请选择结束日期" readonly>
	            <span class="add-on"><i class="icon-th"></i></span>
	        </div>
	        <div class="input-append date" id="startTime" data-date="" data-date-format="dd-mm-yyyy">
	            	开始日期：<input class="span2" id="startDate" size="16" type="text" placeholder="请选择开始日期" readonly>
	            <span class="add-on"><i class="icon-th"></i></span>
	        </div>	
	        <table class="table data-table" id="table">
	            <thead>
	            <tr>
	            	<th></th>
	                <th>流水号</th>
	                <th>客户姓名</th>
	                <th>证件号码</th>
	                <th>合作方</th>
	                <th>分公司</th>
	                <th>个贷服务中心</th>
	                <th>面签用户姓名</th>
	                <th>操作</th>
	            </tr>
	            </thead>
	            <tbody id="videoList">
	            </tbody>
	        </table>
	        <table id="recordTable" style="display:none;"></table>
	    </div>
	</div>
