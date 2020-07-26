//package com.tmdrk.ace.admin.LogFilter;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import org.apache.logging.log4j.core.util.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.support.StandardServletMultipartResolver;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @ClassName LogFilter
// * @Description TODO
// * @Author zhoujie
// * @Date 2020/5/5 16:05
// * @Version 1.0
// **/
//public class LogFilter extends OncePerRequestFilter {
//    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
//
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////
////        String uri = request.getRequestURI();
////        String contextPath = request.getContextPath();
////        String url = uri.substring(contextPath.length());
////        //静态资源 跳过
////        if (url.contains(".")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
//////		输出请求体
////        String requestBody = "";
////        String requestContentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
////
////        if (requestContentType != null){
//////			xml json
////            if (requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE) || requestContentType.startsWith(MediaType.APPLICATION_XML_VALUE)){
////                requestBody = getRequestBody(request);
////                final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
////                request = new HttpServletRequestWrapper(request) {
////                    @Override
////                    public ServletInputStream getInputStream() throws IOException {
////                        return new ByteArrayServletInputStream(byteArrayInputStream);
////
////                    }
////                };
//////		    普通表单提交
////            }else if (requestContentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
////                requestBody = toJson(request.getParameterMap());
//////			文件表单提交
////            }else if (requestContentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)){
////                requestBody = getFormParam(request);
////            }
////        }
////
////        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////        response = new HttpServletResponseWrapper(response) {
////            @Override
////            public ServletOutputStream getOutputStream() throws IOException {
////                return new ByteArrayServletOutputStream(super.getOutputStream(), byteArrayOutputStream);
////            }
////        };
////
////        long requestTime = System.currentTimeMillis();
////
////        filterChain.doFilter(request, response);
////
////        long costTime = System.currentTimeMillis() - requestTime;
////
////        String responseBody = "";
//////		暂定只有json 输出响应体
////        String contentType = response.getHeader(HttpHeaders.CONTENT_TYPE);
////        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)){
////            responseBody = byteArrayOutputStream.toString();
////        }
////
////        if (response.getStatus() >= 200 && response.getStatus() < 300) {
////            log.info("URL:{}, total time:{} ms, responseCode:{}, requestBody:{}, responseBody:{}", url, costTime, response.getStatus(), requestBody, responseBody);
////        }else {
////            log.error("URL:{}, total time:{} ms, responseCode:{}, requestBody:{}, responseBody:{}", url, costTime, response.getStatus(), requestBody, responseBody);
////        }
////    }
////
//
//    private int maxPayloadLength = 1000;
//
//    private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
//        if (buf == null || buf.length == 0) return "";
//        int length = Math.min(buf.length, this.maxPayloadLength);
//        try {
//            return new String(buf, 0, length, charsetName);
//        } catch (UnsupportedEncodingException ex) {
//            return "Unsupported Encoding";
//        }
//    }
//
//    /**
//     * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
//     * @param request the request
//     * @param response the response
//     * @param filterChain chain of filters
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String uri = request.getRequestURI();
//        String contextPath = request.getContextPath();
//        String url = uri.substring(contextPath.length());
//        //静态资源 跳过
//        if (url.contains(".")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String requestContentType = request.getHeader(HttpHeaders.CONTENT_TYPE); //请求类型
//
//        long startTime = System.currentTimeMillis();
//        StringBuilder builderInfo = new StringBuilder();
//        builderInfo.append(request.getMethod());
//        builderInfo.append(" ");
//        builderInfo.append(request.getRequestURL());
//
//        String queryString = request.getQueryString();//url后面的请求参数
//        if (queryString != null) {
//            builderInfo.append("?").append(queryString);
//        }
//
//        if (request.getAuthType() != null) {
//            builderInfo.append(", authType=")
//                    .append(request.getAuthType());
//        }
//        if (request.getUserPrincipal() != null) {
//            builderInfo.append(", principalName=")
//                    .append(request.getUserPrincipal().getName());
//        }
//
//        // ========= Log request and response payload ("body") ========
//        // We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
//        //    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
//        // So we need to apply some stronger magic here :-)
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//
//        //输出请求体
//        //严格来讲打印参数不应该放在filter里面，因为ServletRequest的getReader()和getInputStream()两个方法只能被调用一次，
//        //而且不能两个都调用。打印参数最好用Spring AOP实现
//        String requestBody = "";
//        if (requestContentType != null){
//            // wrappedRequest.getRequest();可找回原request对象；
//            //xml json 提交
//            if (requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE) || requestContentType.startsWith(MediaType.APPLICATION_XML_VALUE)){
//                requestBody = getRequestBody(wrappedRequest);
//                //普通表单提交
//            }else if (requestContentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
//                requestBody = toJson(wrappedRequest.getParameterMap());
//                //文件表单提交
//            }else if (requestContentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)){
//                requestBody = toJson(getFormParam(wrappedRequest));
//            }
//        }
//        builderInfo.append(";requestBody:\n").append(requestBody);
//        logger.info("======>> {}",builderInfo.toString());
////        System.out.println("======================================"+IOUtils.toString(request.getReader()));
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        filterChain.doFilter(wrappedRequest, wrappedResponse);     // 实际请求!
//
//        long duration = System.currentTimeMillis() - startTime; //耗时
//
//        builderInfo.delete(0,builderInfo.length()); //清空
//
//        builderInfo.append("响应状态：").append(response.getStatus()).append("，耗时：")
//                .append(duration).append("ms").append(";responseBody:\n");
//
//        builderInfo.append(getContentAsString(wrappedResponse.getContentAsByteArray(), this.maxPayloadLength, response.getCharacterEncoding()));
//
//        logger.info("<<====== {}",builderInfo.toString());
//        wrappedResponse.copyBodyToResponse();  // 重要: 将响应内容重新赋回源response
//    }
//
//
//    /**
//     * @Author zhoujie
//     * @Description 当xml json请求类型时，获取请求体
//     * @Date 21:23 2020/5/5
//     * @Param [request]
//     * @return java.java.lang.String
//     **/
//    private String getRequestBody(HttpServletRequest request) {
//        int contentLength = request.getContentLength();
//        if(contentLength <= 0){
//            return "";
//        }
//        try {
//            //ServletRequest的getReader()和getInputStream()两个方法只能被调用一次，而且不能两个都调用。
//            //那么如果Filter中调用了一次，在Controller里面就不能再调用了
//            //会抛出异常:getReader() has already been called for this request异常
//            return IOUtils.toString(request.getReader());
//        } catch (IOException e) {
//            logger.error("获取请求体失败", e);
//            return "";
//        }
//    }
//
//    /**
//     * @Author zhoujie
//     * @Description 当multipart/form-data请求类型时，获取请求体
//     * @Date 21:25 2020/5/5
//     * @Param [request]
//     * @return java.util.Map<java.java.lang.String,java.java.lang.Object>
//     **/
//    private Map<String,Object> getFormParam(HttpServletRequest request) {
//        MultipartResolver resolver = new StandardServletMultipartResolver();
//        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);
//
//        Map<String,Object> param = new HashMap<>();
//        Map<String,String[]> parameterMap = mRequest.getParameterMap();//当类型为application/json和application/xml时，为空
//        if (!parameterMap.isEmpty()){
//            param.putAll(parameterMap);
//        }
//        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
//        if(!fileMap.isEmpty()){
//            for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
//                MultipartFile file = fileEntry.getValue();
//                param.put(fileEntry.getKey(), file.getOriginalFilename()+ "(" + file.getSize()+" byte)");
//            }
//        }
//        return param;
//    }
//
//    private static String toJson(Object object){
//        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue);
//    }
//}
