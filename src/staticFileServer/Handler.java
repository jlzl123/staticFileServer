package staticFileServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import staticFileServer.type.MIME;
//������ͷ���д���
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
				//�������ͷ��һ����Ϣ
				String hander=reader.readLine();// ��ȡ������������͹������������ͷ����������Ϣ
				System.out.println("�ͻ��˷��͵�������Ϣ: >>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(hander);//   GET /index.html HTTP/1.1
				String resource=hander.split(" ")[1];// ����������Դ�ĵ�ַ
				System.out.println("�ͻ��˷��͵�������Ϣ���� <<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println("�û��������Դresource��:" + resource);
                System.out.println();
                String suffix=null;
                
                //��������ļ�����
                if(resource.equals("/")){
//                	resource="/index.html";//Ĭ��ҳ��
//                	String[] names=resource.split("\\.");//ƥ��������ʽ����һ��\ת���,����\��.
//                	
//                	suffix=names[names.length-1];//ȡ���ļ�����
//                	contentType=type.get(suffix);
                	File file=new File("src/file");
                	functions.readFileName(file, client);
                	return;
                }else{
                	String[] names=resource.split("\\.");
                	suffix=names[names.length-1];
                	contentType=type.get(suffix);
                }
                
                //�����ļ���·��
                String path="src/file"+resource;
                File file=new File(path);
                System.out.println("********�ļ�λ��"+file.getAbsolutePath());
                if(file.exists()){
                	if(suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("png")){
                		functions.readImg(file, client, contentType);
                	}else{
                		functions.readFile(file, client, contentType);
                	}
                }else{
                	System.out.println("��Դδ�ҵ�");
                	PrintWriter out=new PrintWriter(client.getOutputStream(),true);
                	// ����Ӧ����Ϣ,������Ӧ��
                	out.println("HTTP/1.0 404 NOTFOUND");
                	out.println("Content-Type:text/html;chartset=UTF-8");
                	out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ
                	out.println("�Բ�����Ѱ�ҵ���Դ�ڱ��������ϲ�����");
                	out.close();
                	functions.closeSocket(client);
                }
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("HTTP����������:" + e.getLocalizedMessage());
			}
		}
	}

}
