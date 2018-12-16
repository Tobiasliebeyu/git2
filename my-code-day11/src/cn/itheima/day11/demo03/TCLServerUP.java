package cn.itheima.day11.demo03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/*文件上传案例服务器端:读取客户端上传的文件,保存到服务器的硬盘,给客户端回写"上传成功"

    明确:
        数据源:客户端上传的文件
        目的地:服务器的硬盘 d:\\upload\\1.jpg

1.创建一个服务器ServerSocket对象,和系统要指定的端口号
         2.使用ServerSocket对象中的方法accept,获取到请求的客户端Socket对象
         3.使用Socket对象中的方法getInputStream,获取到网络字节输入流InputStream对象
         4.判断d:\\upload文件夹是否存在,不存在则创建
         5.创建一个本地字节输出流FileOutputStream对象,构造方法中绑定要输出的目的地
         6.使用网络字节输入流InputStream对象中的方法read,读取客户端上传的文件
         7.使用本地字节输出流FileOutputStream对象中的方法write,把读取到的文件保存到服务器的硬盘上
         8.使用Socket对象中的方法getOutputStream,获取到网络字节输出流OutputStream对象
         9.使用网络字节输出流OutputStream对象中的方法write,给客户端回写"上传成功"
         10.释放资源(FileOutputStream,Socket,ServerSocket)*/
public class TCLServerUP {
    public static void main(String[] args) throws IOException {
        //服务器端只能穿件ServerSocket对象,然后再通过accept方法得到客户端的socket对象,来调用方法
        //创建ServerSocket对象的时候参数需要自己指定,不然系统会随机给一个端口号,但是这样的话客户端就找不到需要访问的软件了.所以必须指定
        ServerSocket serverSoc = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSoc.accept();


            //为每个访问到这个服务器的客户端,开辟一个新的线程独立完成上传任务
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //通过socket调用字节输入流,为了读取客户端上传的文件做准备
                        InputStream is = socket.getInputStream();
                        //指定一个文件夹用来储存客户端上传的文件
                        File file = new File("D:\\Develop");
                        //如果该文件夹没有的,我们需要创建一个,mkdir方法会先判断是否存在该文件夹,如果不存在才会进创建
                        file.mkdir();
                        String name = "itheima" + System.currentTimeMillis() + (new Random().nextInt(99999)) + ".avi";
                        //创建服务器本地的字节输出流,将服务器读取完毕的数据存入服务器自己的硬盘当中
                        FileOutputStream fis = new FileOutputStream(file + "\\" + name);

                        byte[] bytes = new byte[1024];
                        int len;
                        //先用服务器端的字节输入流读取上传文件,然后通过服务器本地的字节输出流存储文件
                        while ((len = is.read(bytes)) != -1) {
                            fis.write(bytes, 0, len);
                        }
                        //同样,需要与客户端进行数据传输(交互),我们需要运用socket调用字节输出流write文件或信息传递给客户端
                        OutputStream os = socket.getOutputStream();
                        os.write("上传成功".getBytes());
                        //最后需要释放除了服务器以外的所有其他资源,否则会出现只有这些资源关闭才会将他们刷新(write)到硬盘上.
                        fis.close();
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        System.out.println(e);

                    }
                }


            }).start();

//         fis.close();
//         os.close();
//         is.close();
        }
    }
}
