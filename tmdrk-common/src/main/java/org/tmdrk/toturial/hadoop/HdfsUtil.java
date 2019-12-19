package org.tmdrk.toturial.hadoop;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsUtil {
    private static final String ADDR = "hdfs://local30:9000/";
    public static void main(String[] args) throws Exception{
//        testUpload();
//        download("/Test.txt","D:/Files/test2.txt");
//        mkdir("/aaa/bbb/ccc");
//        remove("/aaa");
//        listFiles("/");
        testDownLoad();
    }

    public static void testUpload() throws Exception{
        //to upload file to hdfs
        Configuration cfg = new Configuration();
        cfg.set("fs.default.name", "hdfs://local30:9000");
        cfg.set("dfs.client.use.datanode.hostname", "true");//让可以使用主机名传参数
        FileSystem fs = FileSystem.get(new URI("hdfs://local30:9000/"),cfg,"hadoop");
        fs.copyFromLocalFile(new Path("F:/upload/test2.txt"),new Path("/"));
        fs.close();
        System.out.println("over");
    }

    public static void testDownLoad() throws Exception{
        //to upload file to hdfs
        Configuration cfg = new Configuration();
        cfg.set("fs.default.name", "hdfs://local32:9000");
        cfg.set("dfs.client.use.datanode.hostname", "true");//让可以使用主机名传参数
        FileSystem fs = FileSystem.get(cfg);
        Path src = new Path("hdfs://local32:9000/Test.txt");
        FSDataInputStream fis = fs.open(src);
        FileOutputStream fos = new FileOutputStream("D:/Files/test3.txt");
        IOUtils.copy(fis,fos);
        System.out.println("over");
    }

    public static FileSystem initFileSystem()throws Exception{
        Configuration cfg = new Configuration();
        cfg.set("fs.default.name", "hdfs://local30:9000");
        cfg.set("dfs.client.use.datanode.hostname", "true");//让可以使用主机名传参数
        FileSystem fs = FileSystem.get(new URI("hdfs://local30:9000/"),cfg," ");
        return fs;
    }

    public static void upload(String srcPath,String desPath)throws Exception{
        FileSystem fs = initFileSystem();
        fs.copyFromLocalFile(new Path(srcPath),new Path(desPath));
        fs.close();
    }

    public static void download(String srcPath,String desPath)throws Exception{
        FileSystem fs = initFileSystem();
        //第一个false参数表示不删除源文件，第4个true参数表示使用本地原文件系统，因为这个Demo程序是在Windows系统下运行的。
        fs.copyToLocalFile(false,new Path(srcPath),new Path(desPath),true);
        fs.close();
    }

    public static void listFiles(String path)throws Exception{
        FileSystem fs = initFileSystem();
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path(path), true);
        while(files.hasNext()){
            LocatedFileStatus file = files.next();
            Path filePath = file.getPath();
            String name = filePath.getName();
            System.out.println("name:"+name);
        }
        System.out.println("--------------------------");
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));
        for(FileStatus fstatus:fileStatuses) {
            System.out.println("name:"+fstatus.getPath().getName()+" isFile:"+fstatus.isFile());
        }
    }

    public static void mkdir(String path)throws Exception {
        FileSystem fs = initFileSystem();
        fs.mkdirs(new Path(path));
        fs.close();
    }
    public static void remove(String path)throws Exception{
        FileSystem fs = initFileSystem();
        fs.delete(new Path(path),true);
    }
}
