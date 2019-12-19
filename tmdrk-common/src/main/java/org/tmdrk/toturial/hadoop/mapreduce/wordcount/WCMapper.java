package org.tmdrk.toturial.hadoop.mapreduce.wordcount;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 4个泛型前两个是mapper输入类型，后两个是mapper的输出类型，同时也对应reduce的输入类型
 * mapper和reduce的数据输入和输出都是以key—value的形式封装的
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 *     KEYIN 要处理的文本中一行的偏移量
 *     VALUEIN 这一行的内容
 *     KEYOUT
 *     VALUEOUT
 *
 *     LongWritable  Text是hadoop自己分装的数据类型Long String，序列化更快捷
 */
public class WCMapper extends Mapper<LongWritable, Text,Text,LongWritable> {
    //maoreduce框架每读一行数据就调用一次该方法
    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        //具体业务逻辑就写在该方法中
        //将一行文本转换成string
        String line = value.toString();
        //将一行文本按特定分隔符切分
        String[] words = StringUtils.split(line, " ");
        for(String word:words){
            context.write(new Text(word),new LongWritable(1));
        }
    }
}
