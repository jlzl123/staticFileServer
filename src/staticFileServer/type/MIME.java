package staticFileServer.type;

import java.util.HashMap;
//MIME类型支持
public class MIME {
	private HashMap<String, String> mime=new HashMap<String, String>();
	
	public MIME() {
		// TODO Auto-generated constructor stub
		this.mime.put("css", "text/css");
		this.mime.put("gif", "image/gif");
		this.mime.put("html", "text/html;charset=utf-8");
		this.mime.put("ico", "image/icon");
		this.mime.put("jpeg", "image/jpeg");
		this.mime.put("jpg", "image/jpeg");
		this.mime.put("js", "text/javascript");
		this.mime.put("json", "application/json");
		this.mime.put("pdf", "application/pdf");
		this.mime.put("png", "image/png");
		this.mime.put("svg", "image/svg+xml");
		this.mime.put("swf", "application/x-shockwave-flash");
		this.mime.put("tiff", "image/tiff");
		this.mime.put("txt", "text/plain;charset=utf-8");
		this.mime.put("wav", "audio/x-wav");
		this.mime.put("wma", "audio/x-ms-wma");
		this.mime.put("wmv", "audio/x-ms-wmv");
		this.mime.put("xml", "text/xml");
	}
	
	public HashMap<String, String> getMime() {
		return mime;
	}
}
