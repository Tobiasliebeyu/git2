package cn.itheima.day11.Test03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//需求说明：我们来做一个“文件上传案例”，有以下要求：
//        将项目中的一个图片,通过客户端上传至服务器
public class TCPClient {
    public static void main(String[] args) throws IOException {
        //首先创建客户端的Socket对象
        Socket socket = new Socket("127.0.0.1", 8888);
        //通过本地的字节输入流读取文件,然后通过客户端的字节输出流写到服务器上
        FileInputStream fis = new FileInputStream("D:\\17_包装类的概念.avi");
        OutputStream os = socket.getOutputStream();
        byte [] bytes = new byte[1024];
        int len;
        while ((len=fis.read(bytes))!=-1){
            os.write(bytes,0,len);
        }
        //由于通过while ture读取到的文件不会读取到最后一个结束标记,
        // 为了避免服务器的读取阻塞情况需要我们手动加上一个结束标识
        socket.shutdownOutput();
        //准备接收服务器发送过来的信息;
        InputStream is = socket.getInputStream();
        while ((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        socket.close();
        fis.close();



    }
}
