package cn.itheima.day11.Test03;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

//需求说明：我们来做一个“文件上传案例”，有以下要求：
//        将项目中的一个图片,通过客户端上传至服务器
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //创建一个ServerSocket(服务器)对象
        ServerSocket server = new ServerSocket(8888);
        while (true){
            Socket socket = server.accept();
            new Thread(() -> {
                try { //监听到自己相关联的客户端,并创建对象

                    //用服务器自己的字节输入流读取客户端上传的信息(文件),然后用服务器本地的字节输出流写到目的地
                    //首先还需要判断目的地的(文件夹)是否存在,不然需要创建
                    File file = new File("D:\\Develop");
                    file.mkdir();
                    String str = "iteheima"+System.currentTimeMillis()+new Random().nextInt(9999)+".avi";
                    FileOutputStream fos = new FileOutputStream(file + "\\" + str);
                    InputStream is = socket.getInputStream();
                    int len ;
                    byte [] bytes=new byte[1024];
                    while ((len=is.read(bytes))!=-1){
                        fos.write(bytes,0,len);
                    }
                    OutputStream os = socket.getOutputStream();
                    os.write("上传完毕".getBytes());
                    socket.close();
                    fos.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }).start();
        }

       // server.close();

    }
}
