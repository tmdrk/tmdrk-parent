package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.baseAnnotation.*;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.*;

import java.lang.annotation.*;


/**
 * @ClassName Properties
 * @Description 字段属性解释参考：https://blog.csdn.net/zx711166/article/details/81667862
 * @Author zhoujie
 * @Date 2019/12/18 17:36
 * @Version 1.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertiesNew {

    //字符串：string，string类型包含 text 和 keyword。
    //text：该类型被用来索引长文本，在创建索引前会将这些文本进行分词，转化为词的组合，建立索引；允许es来检索这些词，text类型不能用来排序和聚合。
    //keyword：该类型不需要进行分词，可以被用来检索过滤、排序和聚合，keyword类型自读那只能用本身来进行检索（不可用text分词后的模糊检索）
    //数值型：long、integer、short、byte、double、float
    //日期型：date
    //布尔型：boolean
    //二进制型：binary
    //除字符串类型以外，其他类型必须要进行精确查询，因为除字符串外其他类型不会进行分词。

    //复杂数据类型（Complex datatypes）数组类型（Array datatype），数组类型不需要专门指定数组元素的type，例如：
    //字符型数组：[“one”，“two”]
    //整型数组*：[1, 2]
    //数组型数组：[1, [2, 3]] 等价于 [1, 2, 3]
    //对象数组：[{“name”: “Mary”, “age”: 12}, {“name”: “John”, “age”: 10}]
    //对象类型（Object datatype）：object 用于单个Json对象

    //嵌套类型（Nested datatype）：nested 用于Json数组

    //地理位置类型（Geo datatypes）
    // 地理坐标类型（Geo-point datatype）：geo_point 用于经纬度坐标
    //地理形状类型（Geo-Shape datatype）：geo_shape 用于类似于多边形的复杂形状

    //特定类型（Specialised datatypes）
    // IPv4 类型（IPv4 datatype）：ip 用于IPv4 地址
    //Completion 类型（Completion datatype）：completion 提供自动补全建议
    //Token count 类型（Token count datatype）：token_count 用于统计做子标记的字段的index数目，该值会一直增加，不会因为过滤条件而减少
    //mapper-murmur3 类型：通过插件，可以通过_murmur3_来计算index的哈希值
    //附加类型（Attachment datatype）：采用mapper-attachments插件，可支持_attachments_索引，例如 Microsoft office 格式，Open Documnet 格式， ePub，HTML等
    StringAnno type();

    //index：是否构建倒排索引（即是否分词，设置false，字段将不会被索引） true（缺省）| false
    BooleanAnno index() default @BooleanAnno();

    //enabled：仅存储、不做搜索和聚合分析 true （缺省）| false
    BooleanAnno enabled() default @BooleanAnno();

    //index_option：存储倒排索引的哪些信息
    //    4个可选参数
    //    docs：索引文档号
    //    freqs：文档号+词频
    //    positions：文档号+词频+位置，通常用来距离查询
    //    offsets：文档号+词频+位置+偏移量，通常被使用在高亮字段
    //    分词字段默认时positions，其他默认时docs
    //"index_options": "docs"
    IndexOptions index_options() default @IndexOptions();

    //是否归一化相关参数、如果字段仅用于过滤和聚合分析、可关闭
    //分词字段默认配置，不分词字段：默认{“enable”: false}，存储长度因子和索引时boost，建议对需要参加评分字段使用，会额外增加内存消耗
    //"norms": {"enable": true, "loading": "lazy"}
    BooleanAnno norms() default @BooleanAnno();

    //doc_value：是否开启doc_value，用户聚合和排序分析
    //对not_analyzed字段，默认都是开启，分词字段不能使用，对排序和聚合能提升较大性能，节约内存
    //"doc_value":true（缺省）| false
    BooleanAnno doc_value() default @BooleanAnno();

    //fielddata：是否为text类型启动fielddata，实现排序和聚合分析
    //针对分词字段，参与排序或聚合时能提高性能，不分词字段统一建议使用doc_value
    BooleanAnno fielddata() default @BooleanAnno();

    BooleanAnno store() default @BooleanAnno();

    //coerce：是否开启自动数据类型转换功能，比如：字符串转数字，浮点转整型
    BooleanAnno coerce() default @BooleanAnno();

    BooleanAnno dynamic() default @BooleanAnno();

    //data_detection：是否自动识别日期类型
    BooleanAnno data_detection() default @BooleanAnno();

    //analyzer：指定分词器，默认分词器为standard analyzer
    StringAnno analyzer() default @StringAnno(value="standard");

    //boost：字段级别的分数加权，默认值是1.0
    //"boost": 1.23
    FloatAnno boost() default @FloatAnno();

    //fields：可以对一个字段提供多种索引模式，同一个字段的值，一个分词，一个不分词
    //"fields": {"raw": {"type": "string", "index": "not_analyzed"}}
    ClassAnno fields() default @ClassAnno();

    //ignore_above：超过100个字符的文本，将会被忽略，不被索引
    IntAnno ignore_above() default @IntAnno();

    //include_in_all：设置是否此字段包含在_all字段中，默认时true，除非index设置成no
    BooleanAnno include_in_all() default @BooleanAnno();

    //null_value：设置一些缺失字段的初始化，只有string可以使用，分词字段的null值也会被分词
    StringAnno null_value() default @StringAnno(value="NULL");

    //position_increament_gap：影响距离查询或近似查询，可以设置在多值字段的数据上或分词字段上，查询时可以指定slop间隔，默认值时100
    IntAnno position_increament_gap() default @IntAnno(value=100);

    //SearchAnalyzer：设置搜索时的分词器，默认跟analyzer是一致的，比如index时用standard+ngram，搜索时用standard用来完成自动提示功能
    StringAnno search_analyzer() default @StringAnno(value="standard");

    //similarity：默认时TF/IDF算法，指定一个字段评分策略，仅仅对字符串型和分词类型有效
    //"similarity": "BM25"
    StringAnno similarity() default @StringAnno(value="TF/IDF");

    //trem_vector：默认不存储向量信息，支持参数yes（term存储），with_positions（term+位置），with_offsets（term+偏移量），with_positions_offsets（term+位置+偏移量）对快速高亮fast vector highlighter能提升性能，但开启又会加大索引体积，不适合大数据量用
    StringAnno trem_vector() default @StringAnno(value = "no");

}
