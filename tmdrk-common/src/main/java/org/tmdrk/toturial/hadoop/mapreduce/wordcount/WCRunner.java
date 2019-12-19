package org.tmdrk.toturial.hadoop.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 用来描述一个特定的作业
 * 比如，改作业是用哪个类作为逻辑处理中的map，哪个作为reduce
 * 还可以指定该作业要处理的数据所在的路径
 * 还可以指定该作业输出的结果放在哪个路径
 */
public class WCRunner {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //设置整个job所用的那些类在哪个jar包
        job.setJarByClass(WCRunner.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        //指定reduce的输出数据kv类型 (mapper不指定跟谁这个)
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        //指定mapper的输出数据kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //指定本地测试原始数据存放位置
        FileInputFormat.setInputPaths(job,new Path("D:/wc/srcdata"));
        FileOutputFormat.setOutputPath(job,new Path("D:/wc/output"));
        //指定服务器原始数据存放位置
//        FileInputFormat.setInputPaths(job,new Path("/wc/srcdata"));
//        FileOutputFormat.setOutputPath(job,new Path("/wc/output"));
        //将job提交给集群运行;
        job.waitForCompletion(true);
        System.out.println("finish");
    }
}
