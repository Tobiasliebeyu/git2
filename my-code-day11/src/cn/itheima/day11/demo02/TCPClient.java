package cn.itheima.day11.demo02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*1.创建一个本地字节输入流FileInputStream对象,构造方法中绑定要读取的数据源
         2.创建一个客户端Socket对象,构造方法中绑定服务器的IP地址和端口号
         3.使用Socket中的方法getOutputStream,获取网络字节输出流OutputStream对象
         4.使用本地字节输入流FileInputStream对象中的方法read,读取本地文件
         5.使用网络字节输出流OutputStream对象中的方法write,把读取到的文件上传到服务器
         6.使用Socket中的方法getInputStream,获取网络字节输入流InputStream对象
         7.使用网络字节输入流InputStream对象中的方法read读取服务回写的数据
         8.释放资源(FileInputStream,Socket)*/
public class TCPClient {
     public static void main(String[] args) throws IOException {
         //首先创建一个字节输入流的文件(路径)对象,为需要将要读取的数据做准备
         FileInputStream fis = new FileInputStream("D:\\17_包装类的概念.avi");
         //创建一个客户端的Socket对象,参数里面1.服务器的ip地址,首先我们需要找到服务器
         //2.然后找到需要访问的软件端口代号
         Socket socket = new Socket("127.0.0.1", 8888);
         //通过客户端对象调用字节输入流方法
         OutputStream os = socket.getOutputStream();

         byte [] bytes=new byte[1024];
         int len;
         //用本地的字节输入流先读取本地文件,然后通过客户端字节输出流上传至服务器端
         while ((len=fis.read(bytes))!=-1){
             os.write(bytes,0,len);
         }
         //由于读取数据的时候不会读到结束语句,所以当while循环结束时需要给读取的数据的末尾加上一个结束语句,
         // 否则当服务器端利用while循环读取文件的时候,由于没有结束标记会导致读取阻塞,一直停在循环内部,停不下来
         socket.shutdownOutput();
         //通过客户端调用输入字节流,将服务器上的信息读取下来
         InputStream is = socket.getInputStream();
         while ((len=is.read(bytes))!=-1){
             System.out.println(new String(bytes,0,len));

         }
         //关闭所有创建出来的io流
         fis.close();
         os.close();
         is.close();
     }

}
