<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>在线视频</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  </head>
  
  <body style="background-color:#dcdcdc">
    <br/>
	<br/>
	<div>
	
		<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" events="True" width="720" height="540"  codebase="http://download.videolan.org/pub/videolan/vlc/last/win32/axvlc.cab">
			<param name="width" value="300">
			<param name="height" value="300">
			<param name="FileName" value="${cospath}">
			<param name="autoplay" value="false"> 
		</object>
	</div>
  </body>
</html>
