package cn.itheima.day11.TestWeb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//网页的服务器端 准备接收网页发出的请求并响应
public class TestWebUP {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        //监控网页是否有客户端访问,有有就创建Socket对象
        while (true){
            Socket socket = server.accept();
            new Thread(() -> {
                try {
                    InputStream is = socket.getInputStream();
                    //先将字节流转换为字符流,然后再转化为缓冲字符流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String s = br.readLine();
//        System.out.println(s);
                    String[] split = s.split(" ");
                    String s1 = split[1];
                    String htmlpath = s1.substring(1);
                    //先读取本地的网页内容,然后响应回传数据给网页客户端
                    FileInputStream fis = new FileInputStream(htmlpath);
                    OutputStream os = socket.getOutputStream();

                    // 写入HTTP协议响应头,固定写法
                    os.write("HTTP/1.1 200 OK\r\n".getBytes());
                    os.write("Content-Type:text/html\r\n".getBytes());
                    // 必须要写入空行,否则浏览器不解析
                    os.write("\r\n".getBytes());

                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = fis.read(bytes)) != -1) {
                        os.write(bytes, 0, len);
                    }

                    //server.close();
                    socket.close();
                    fis.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }).start();

        }




    }
}
