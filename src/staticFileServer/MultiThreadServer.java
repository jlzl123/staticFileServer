package staticFileServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        int port=20012;
        try {
        	//
			ServerSocket server=new ServerSocket(port);
			Socket client=null;
			System.out.println("��̬�ļ���������������,�˿�:" + port);
			System.out.println();
			while(true){
				client=server.accept();
				System.out.println(client + "���ӵ�HTTP������");
				Handler handler=new Handler(client);
				new Thread(handler).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
