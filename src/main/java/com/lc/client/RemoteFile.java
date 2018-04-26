package com.lc.client;

import jcifs.UniAddress;
import jcifs.smb.*;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ifly_lc
 * 远程文件共享
 */
public class RemoteFile {
    /**
     * 方法说明：上传文件到远程共享目录
     *
     * @param localDir        本地临时路径(A:/测试/测试.xls)
     * @param removeDir       远程共享路径(smb://10.169.2.xx/测试/,特殊路径只能用/)
     * @param removeIp        远程共享目录IP(10.169.2.xx)
     * @param removeLoginUser 远程共享目录用户名(user)
     * @param removeLoginPass 远程共享目录密码(password)
     * @return
     * @throws Exception 0成功/-1失败
     */
    public static int smbUploading(String localDir, String removeDir,
                                   String removeIp, String removeLoginUser, String removeLoginPass) throws Exception {
        NtlmPasswordAuthentication auth = null;
        OutputStream out = null;
        int retVal = 0;
        try {
            File dir = new File(localDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InetAddress ip = InetAddress.getByName(removeIp);
            UniAddress address = new UniAddress(ip);
            // 权限验证
            auth = new NtlmPasswordAuthentication(removeIp, removeLoginUser, removeLoginPass);
            SmbSession.logon(address, auth);

            //远程路径判断文件文件路径是否合法
            SmbFile remoteFile = new SmbFile(removeDir, auth);
            remoteFile.connect();
            if (remoteFile.isDirectory()) {
                retVal = -1;
            }

            // 向远程共享目录写入文件
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            out.write(toByteArray(dir));
        } catch (UnknownHostException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (MalformedURLException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (SmbException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (IOException e) {
            retVal = -1;
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return retVal;
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param file 文件
     * @return 字节数组
     * @throws IOException IO异常信息
     */
    @SuppressWarnings("resource")
    public static byte[] toByteArray(File file) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void smbPut(String remoteUrl, String localFilePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File localFile = new File(localFilePath);
            System.out.println("bbbbbbbb");
            String fileName = localFile.getName();
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName);

            in = new BufferedInputStream(new FileInputStream(localFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void smbGet(String remoteUrl, String localDir) {

        NtlmPasswordAuthentication auth = null;
        try {
            String userName = "ifly_lc";
            String password = "124578";
            String domainIP = "172.16.4.185";
            InetAddress ip = InetAddress.getByName(domainIP);
            UniAddress myDomain = new UniAddress(ip);
            auth = new NtlmPasswordAuthentication(domainIP, userName, password);  //先登录验证
            System.out.println("auth:" + auth.getDomain());
            System.out.println("username:" + auth.getUsername());
            System.out.println("password:" + auth.getPassword());
            SmbSession.logon(myDomain, auth);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("111!!!");
        } catch (SmbException e) {
            e.printStackTrace();
            System.out.println("222!!!");
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl, auth); //注意，这句是关键，SmbFile 不支持特殊字符的密码，因此在这里放置
            //remoteFile.connect();
            if (remoteFile == null) {
                //log.error("共享文件不存在！000");
                return;
            }
            SmbFile localFile = new SmbFile(localDir, auth);
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(localFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RemoteFile remoteFile = new RemoteFile();
        String localDir = "C:/Users/ifly_lc/Desktop/prompt/lclc1.wav";
        String remoteDir = "smb://172.16.4.81/Share/lclc1.wav";
        String remoteIp = "172.16.4.81";
        String remoteUser = "Li Qi";
        String remotePassword = "liqi123456";
        try {
            //remoteFile.smbUploading(localDir,remoteDir,remoteIp,remoteUser,remotePassword);
            File localFile = new File("C:/Users/ifly_lc/Desktop/prompt");
            File[] waveFileList = localFile.listFiles();

            for (int index = 0; index < waveFileList.length; index++) {
                //smbPut("smb://ifly_lc:124578@172.16.4.185/lc", waveFileList[index].getPath());
                smbUploading(localDir, remoteDir, remoteIp, remoteUser, remotePassword);
                //smbGet(remoteDir,localDir);
                //smbPut("smb://Administrator:@172.16.4.88/DiyRing", waveFileList[index].getPath());
                //smbPut("smb://Li Qi:liqi123456@172.16.4.81/Share", waveFileList[index].getPath());
                //smbUploading(waveFileList[index].getPath(),remoteDir,remoteIp,remoteUser,remotePassword);
                //smbPut("smb://root:iflytek@172.16.4.205/lc", waveFileList[index].getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
