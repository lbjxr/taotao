package com.taotao.controller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//测试ftp服务器
public class FtpTest {

    public static void main(String[] args) throws IOException {
        //创建一个FTPClient对象
        FTPClient ftpClient = new FTPClient();
        //创建ftp连接，默认是21端口
        ftpClient.connect("192.168.142.128",21);
        //登录到ftp的账号和密码
        ftpClient.login("ftpuser", "j5i2a1nn5i2n");
        //设置本地文件路径，读取文件
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\LBJ\\Documents\\Default.rdp"));
        //设置文件编码
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //上传的路径
        ftpClient.changeWorkingDirectory("/home/ftpuser/");
        //报错路径
        ftpClient.storeFile("Default.rdp", inputStream);

        ftpClient.logout();
    }
}
