package cn.itheima.day11.Test;

import java.io.*;
import java.net.Socket;

/*需求说明：创建新项目，按以下要求编写代码：
        在项目下创建TCP 客户端
        访问之前创建的服务器端,服务器端ip127.0.0.1 端口号8888
        1:客户端连接服务器,并发送 hello.服务器,我是客户端.
        2:开启上一题服务器,等待客户端连接,客户端连接并发送数据*/
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream os = socket.getOutputStream();
        os.write("hello.服务器,我是客户端".getBytes());
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
//        FileOutputStream fos = new FileOutputStream("D;\\a.txt");
        int len;
        byte [] bytes=new byte[1024];
        while ((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));

        }
        socket.close();

    }
}
