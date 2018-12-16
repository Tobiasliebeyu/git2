package cn.itheima.day11.Test02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//需求说明：使用TCP编写一个网络程序,设置服务器程序监听端口为8002,
//        当于客户端建立后,向客户端发送”hello world”,客户端将信息输出
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8002);
        //server.accept()此代码是通过监听有没有新的客户端关联到自己,
        // 如果有就可以通过此方法创建Socket对象,从而跟客户端进行数据交互
        Socket socket = server.accept();
        OutputStream os = socket.getOutputStream();
        os.write("hello world".getBytes());
        server.close();
        socket.close();

    }
}
