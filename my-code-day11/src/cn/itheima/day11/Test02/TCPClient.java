package cn.itheima.day11.Test02;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

//需求说明：使用TCP编写一个网络程序,设置服务器程序监听端口为8002,
//        当于客户端建立后,向客户端发送”hello world”,客户端将信息输出
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8002);
        InputStream is = socket.getInputStream();
        byte [] bytes= new byte[1024];
        int len =is.read(bytes);
        System.out.println(new String(bytes,0,len));
        socket.close();
    }

}
