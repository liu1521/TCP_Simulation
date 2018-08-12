package init.liu.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by : liu
 * Create on : 2018/8/12
 * Create for : 服务端
 */

public class Server {

    private ServerSocket ss;

    private Socket socket;

    private File file;

    private Server() {
        try {
            file = new File("Server.txt");
            if (!file.exists()) file.createNewFile();
            ss = new ServerSocket(12345);
            //不断接收客户端的连接请求,每当接收到一个请求开启一个线程进行操作
            while (true) {
                socket = ss.accept();
                new Thread(new ServerThread(socket, file)).start();
            }
        } catch (IOException e) {
            System.out.println("端口被占用,程序退出");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}