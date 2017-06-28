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

	//�ر�socket
	public void closeSocket(Socket socket) {
		// TODO Auto-generated method stub
       if(socket!=null){
    	   try {
			socket.close();
			System.out.println(socket+"�뿪��http��������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
	}
	
	/*
	 * PrintWriter���������ַ���
	 * PrintStream��Ҫ����byte��
	 * Java��һ���ַ���char����16bit�ģ�һ��BYTE��8bit�� 
     * PrintStrean��д��һ��8bit�����ݵġ� 
     * PrintWriter��д��һ��16bit�����ݵġ� 
     * Stringȱʡ����UNICODE���룬��16bit�ġ������PrintWriterд����ַ���
	 */
	public void readImg(File file,Socket client,String contentType){
		PrintStream out=null;
		FileInputStream fis=null;
		try {
			//�����µĴ�ӡ���� out - �������ӡֵ�Ͷ���������,autoFlush - boolean ������
			//���Ϊ true����ÿ��д�� byte ���顢��������һ�� println ������д�뻻�з����ֽ� ('\n') ʱ����ˢ�»�����
			out=new PrintStream(client.getOutputStream(), true);
			fis=new FileInputStream(file);
			//available���ؿ��Բ��������شӴ��������ж�ȡ�����������Ĺ���ʣ���ֽ����� 
			byte[] data=new byte[(fis.available())];
			out.println("HTTP/1.0 200 ok");
			out.println("ContentType:"+contentType);
			out.println("Content-Length:"+file.length());
			out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ
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
			out.println("HTTP/1.0 200 0k");//������println,ͷ��Ϣ�ֿ�
			out.println("ContentType:"+contentType);
			out.println();
			while((s=buff.readLine())!=null){
				out.print(s);
			}
			buff.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("HTTP/1.0 500");// ����Ӧ����Ϣ,������Ӧ��
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
			out.println("HTTP/1.0 500");// ����Ӧ����Ϣ,������Ӧ��
            out.println("");
            out.flush();
		}finally{
			out.close();
			closeSocket(client);//�رն˿ڼ���
		}
	}
}
