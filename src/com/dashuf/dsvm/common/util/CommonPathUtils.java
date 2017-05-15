package com.dashuf.dsvm.common.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonPathUtils {
	private static final String PATH_DELIMITER = "/";

	  public static String encodeRemotePath(String urlPath)
	    throws Exception
	  {
	    StringBuilder pathBuilder = new StringBuilder();
	    String[] pathSegmentsArr = urlPath.split("/");

	    for (String pathSegment : pathSegmentsArr) {
	      if (!pathSegment.isEmpty()) {
	        try {
	          pathBuilder.append("/").append(URLEncoder.encode(pathSegment, "UTF-8").replace("+", "%20"));
	        }
	        catch (UnsupportedEncodingException e) {
	          String errMsg = "Unsupported ecnode exception:" + e.toString();
	          throw new Exception(errMsg);
	        }
	      }
	    }
	    if (urlPath.endsWith("/")) {
	      pathBuilder.append("/");
	    }
	    return pathBuilder.toString();
	  }
}
