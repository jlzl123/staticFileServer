package staticFileServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Functions {

	//关闭socket
	public void closeSocket(Socket socket) {
		// TODO Auto-generated method stub
       if(socket!=null){
    	   try {
			socket.close();
			System.out.println(socket+"离开了http服务器！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
	}
	
	/*
	 * PrintWriter用来操作字符流
	 * PrintStream主要操作byte流
	 * Java的一个字符（char）是16bit的，一个BYTE是8bit的 
     * PrintStrean是写入一串8bit的数据的。 
     * PrintWriter是写入一串16bit的数据的。 
     * String缺省是用UNICODE编码，是16bit的。因此用PrintWriter写入的字符串
	 */
	public void readImg(File file,Socket client,String contentType){
		PrintStream out=null;
		FileInputStream fis=null;
		try {
			//创建新的打印流， out - 将向其打印值和对象的输出流,autoFlush - boolean 变量；
			//如果为 true，则每当写入 byte 数组、调用其中一个 println 方法或写入换行符或字节 ('\n') 时都会刷新缓冲区
			out=new PrintStream(client.getOutputStream(), true);
			fis=new FileInputStream(file);
			//available返回可以不受阻塞地从此输入流中读取（或跳过）的估计剩余字节数。 
			byte[] data=new byte[(fis.available())];
			out.println("HTTP/1.0 200 ok");
			out.println("ContentType:"+contentType);
			out.println("Content-Length:"+file.length());
			out.println();// 根据 HTTP 协议, 空行将结束头信息
			fis.read(data);
			out.write(data);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("HTTP/1.0 500");
			out.println();
			out.flush();
		}finally{
			out.close();
			closeSocket(client);
		}
	}
	
	public void readFile(File file,Socket client,String contentType){
		PrintWriter out=null;
		FileReader reader=null;
		try {
			out=new PrintWriter(client.getOutputStream(), true);
			reader=new FileReader(file);
			BufferedReader buff=new BufferedReader(reader);
			String s=null;
			out.println("HTTP/1.0 200 0k");//必需是println,头信息分开
			out.println("ContentType:"+contentType);
			out.println();
			while((s=buff.readLine())!=null){
				out.print(s);
			}
			buff.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("HTTP/1.0 500");// 返回应答消息,并结束应答
            out.println("");
            out.flush();
		}finally{
			out.close();
			closeSocket(client);
		}
	}
	
	public void getFile(File file,List<String> list) {
		// TODO Auto-generated method stub
        File[] fileArray=file.listFiles();
        for(File f:fileArray){
        	if(f.isDirectory()){
        		getFile(f, list);
        	}else{
        	   String fileName=f.getName();
        	   list.add(fileName);
        	}
        }
	}
	
	public void readFileName(File file,Socket client){
		File[] fileArray=file.listFiles();
		PrintWriter out=null;
		try {
			out=new PrintWriter(client.getOutputStream(), true);
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type:text/html;charset=utf-8");
			out.println();
			for(File f:fileArray){
				String fileName=f.getName();
				out.println("<a href='"+fileName+"'>"+fileName+"</a></br>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("HTTP/1.0 500");// 返回应答消息,并结束应答
            out.println("");
            out.flush();
		}finally{
			out.close();
			closeSocket(client);//关闭端口监听
		}
	}
}
