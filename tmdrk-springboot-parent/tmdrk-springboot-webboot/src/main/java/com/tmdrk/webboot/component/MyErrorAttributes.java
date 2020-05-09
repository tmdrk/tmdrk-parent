package com.tmdrk.webboot.component;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @ClassName MyErrorAttributes
 * @Description 给容器中添加自定义的Error属性
 * @Author zhoujie
 * @Date 2020/5/7 2:09
 * @Version 1.0
 **/
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("company","tmdrk");

        //将MyExceptionHandler存入的ext值取出，0表示request请求阈
        Map<String,Object> ext = (Map<String,Object>)webRequest.getAttribute("ext", 0);
        errorAttributes.put("ext",ext);
        return errorAttributes;
    }
}
