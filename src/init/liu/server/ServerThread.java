package init.liu.server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by : liu
 * Create on : 2018/8/12
 * Create for : 接受客户端发送的消息并写入文本
 */

public class ServerThread implements Runnable {

    private Socket socket;

    //将socket的字节输出流包装成字符流便于操作
    private BufferedWriter bw;

    private StringBuffer text;

    private SimpleDateFormat time;

    //将socket的字节输入流包装成字符流便于操作
    private BufferedReader br;

    public ServerThread(Socket socket, File file) {
        this.socket = socket;
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg;

        //不断接受客户端发来的消息直到客户端断开连接
        while (true) {
            try {
                msg = br.readLine();
                if (msg == null) continue;
                text = new StringBuffer();
                write(msg);
            } catch (IOException e) {
                try {
                    socket.close();
                    break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    //这里直接抛出异常便于run()方法中捕捉异常后断开连接
    private synchronized void write(String msg) throws IOException {
        text.append(msg).append("\t").append(time.format(new Date())).append("\t")
                .append(socket.getInetAddress()).append("\t").append(socket.getPort());
        bw.write(text.toString());
        bw.newLine();
        bw.flush();
    }
}
