package init.liu.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Create by : liu
 * Create on : 2018/8/12
 * Create for : 客户端
 */

public class Client {

    Socket socket;
    Scanner in;
    BufferedWriter bw;

    private Client() {
        in = new Scanner(System.in);
        try {
            socket = new Socket("127.0.0.1", 12345);
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMsg();
    }

    private void sendMsg() {
        //可以一直发送消息,直到服务端关闭连接后关闭流并退出
        while (true) {
            String test = in.nextLine();
            try {
                bw.write(test);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                System.out.println("连接已断开");
                try {
                    socket.close();
                    break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
