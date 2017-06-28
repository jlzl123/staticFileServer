package staticFileServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import staticFileServer.type.MIME;
//对请求头进行处理
public class Handler implements Runnable{
	private Functions functions=new Functions();
	private MIME mime=new MIME();
	HashMap<String, String> type=mime.getMime();
	String contentType=null;
	public String encode="UTF-8";
	private Socket client;
//	PrintWriter out=null;

	public Handler(Socket socket) {
		// TODO Auto-generated constructor stub
		this.client=socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(client!=null){
			try {
				BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
				//获得请求头第一行信息
				String hander=reader.readLine();// 读取所有浏览器发送过来的请求参数头部的所有信息
				System.out.println("客户端发送的请求信息: >>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(hander);//   GET /index.html HTTP/1.1
				String resource=hander.split(" ")[1];// 获得请求的资源的地址
				System.out.println("客户端发送的请求信息结束 <<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println("用户请求的资源resource是:" + resource);
                System.out.println();
                String suffix=null;
                
                //获得请求文件类型
                if(resource.equals("/")){
//                	resource="/index.html";//默认页面
//                	String[] names=resource.split("\\.");//匹配正则表达式，第一个\转义符,陪陪\和.
//                	
//                	suffix=names[names.length-1];//取得文件类型
//                	contentType=type.get(suffix);
                	File file=new File("src/file");
                	functions.readFileName(file, client);
                	return;
                }else{
                	String[] names=resource.split("\\.");
                	suffix=names[names.length-1];
                	contentType=type.get(suffix);
                }
                
                //本地文件库路径
                String path="src/file"+resource;
                File file=new File(path);
                System.out.println("********文件位置"+file.getAbsolutePath());
                if(file.exists()){
                	if(suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("png")){
                		functions.readImg(file, client, contentType);
                	}else{
                		functions.readFile(file, client, contentType);
                	}
                }else{
                	System.out.println("资源未找到");
                	PrintWriter out=new PrintWriter(client.getOutputStream(),true);
                	// 返回应答消息,并结束应答
                	out.println("HTTP/1.0 404 NOTFOUND");
                	out.println("Content-Type:text/html;chartset=UTF-8");
                	out.println();// 根据 HTTP 协议, 空行将结束头信息
                	out.println("对不起，您寻找的资源在本服务器上不存在");
                	out.close();
                	functions.closeSocket(client);
                }
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("HTTP服务器错误:" + e.getLocalizedMessage());
			}
		}
	}

}
