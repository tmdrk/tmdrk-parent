package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.analyzerAnnotation.CharFilter;
import com.tmdrk.chat.common.entity.es.analyzerAnnotation.Filter;
import com.tmdrk.chat.common.entity.es.analyzerAnnotation.Tokenizer;
import com.tmdrk.chat.common.entity.es.fieldAnnotation.Analyzer;

/**
 * @ClassName Analysis
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 11:59
 * @Version 1.0
 **/
public class Analysis {
    @CharFilter()
    String char_filter;
    @Tokenizer
    String tokenizer;
    @Filter
    String filter;
    @Analyzer
    String analyzer;
}
