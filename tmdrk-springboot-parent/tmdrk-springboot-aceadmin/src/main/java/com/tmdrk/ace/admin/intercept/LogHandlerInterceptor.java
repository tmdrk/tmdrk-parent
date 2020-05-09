//package com.tmdrk.ace.admin.intercept;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.google.common.collect.Maps;
//import com.tmdrk.ace.admin.util.IpUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @ClassName LogHandlerInterceptor
// * @Description TODO
// * @Author zhoujie
// * @Date 2020/5/5 13:00
// * @Version 1.0
// **/
//
//public class LogHandlerInterceptor implements HandlerInterceptor {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
//     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Map<String,Object> jsonMap = Maps.newHashMap();
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        String requestedSessionId = request.getRequestedSessionId();
//        String threadName = Thread.currentThread().getName();
//        StringBuffer requestURL = request.getRequestURL();
//        String sourceIpAddr = IpUtil.getIpAddr2(request);
//        String method = request.getMethod();
//        String contentType = request.getContentType();
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
//        jsonMap.put("requestedSessionId",requestedSessionId);
//        jsonMap.put("requestURL",requestURL);
//        jsonMap.put("threadName",threadName);
//        jsonMap.put("sourceIpAddr",sourceIpAddr);
//        jsonMap.put("hostAddress",hostAddress);
//        jsonMap.put("method",method);
//        jsonMap.put("parameterMap",parameterMap);
//        jsonMap.put("contentType",contentType);
//        logger.info(JSON.toJSONString(jsonMap, SerializerFeature.WriteMapNullValue));
//        return true;
//    }
//
//    /**
//     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
//     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Map<String,Object> jsonMap = Maps.newHashMap();
//        int status = response.getStatus();
//        String contentType = response.getContentType();
//        String threadName = Thread.currentThread().getName();
//        jsonMap.put("threadName",threadName);
//        jsonMap.put("contentType",contentType);
//        jsonMap.put("status",status);
////        jsonMap.put("writer",writer);
//        logger.info(JSON.toJSONString(jsonMap, SerializerFeature.WriteMapNullValue));
//    }
//
//    /**
//     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
//     */
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("afterCompletion");
//    }
//}
