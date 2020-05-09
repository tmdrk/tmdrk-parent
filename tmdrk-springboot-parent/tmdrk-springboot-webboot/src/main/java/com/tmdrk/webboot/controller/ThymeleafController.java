package com.tmdrk.webboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ThymeleafController
 * @Description 测试Thymeleaf模板引擎
 *
 * 属性优先级
 * Order    Feature 						Attributes
 * 1 		Fragment inclusion 				th:insert       （插入片段）
 * 											th:replace
 * 2 		Fragment iteration 				th:each         （迭代）
 * 3 		Conditional evaluation 			th:if           （条件判断）
 * 											th:unless
 * 											th:switch
 * 											th:case
 * 4 		Local variable definition 		th:object       （声明变量）
 * 											th:with
 * 5 		General attribute modification 	th:attr         （通用属性修改）
 * 											th:attrprepend
 * 											th:attrappend
 * 6 		Specific attribute modification th:value        （指定属性修改）
 * 											th:href
 * 											th:src ...
 * 7 		Text (tag body modification) 	th:text         （修改文本值，转义）
 * 											th:utext        （修改文本值，不转义）
 * 8 		Fragment specification 			th:fragment     （声明片段）
 * 9  		Fragment removal 				th:remove       （删除片段）
 *
 *
 * 标准表达式语法
 * 1 Simple expressions:
 * 		Variable Expressions: ${...}  (OGNL表达式)
 * 	        1 获取对象的属性，调用对象的方法
 *          2 使用内置的基本对象
 * 	            #ctx : the context object.
 * 	            #vars: the context variables.
 * 	            #locale : the context locale.
 * 	            #request : (only in Web Contexts) the HttpServletRequest object.
 * 	            #response : (only in Web Contexts) the HttpServletResponse object.
 * 	            #session : (only in Web Contexts) the HttpSession object.
 * 	            #servletContext : (only in Web Contexts) the ServletContext object.
 * 	        3 内置的一些工具对象
 * 	            #execInfo : information about the template being processed.
 * 				#messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using
 * 				#{…} syntax. #uris : methods for escaping parts of URLs/URIs
 * 				#conversions : methods for executing the configured conversion service (if any).
 * 				#dates : methods for java.util.Date objects: formatting, component extraction, etc.
 * 				#calendars : analogous to
 * 				#dates , but for java.util.Calendar objects.
 * 				#numbers : methods for formatting numeric objects.
 * 				#strings : methods for String objects: contains, startsWith, prepending/appending, etc.
 * 				#objects : methods for objects in general.
 * 				#bools : methods for boolean evaluation.
 * 				#arrays : methods for arrays.
 * 				#lists : methods for lists.
 * 				#sets : methods for sets.
 * 				#maps : methods for maps.
 * 				#aggregates : methods for creating aggregates on arrays or collections.
 * 				#ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
 * 		Selection Variable Expressions: *{...} (变量选择表达式，和${}在功能上是一样的)
 * 	         有一个补充功能，配合Object使用
 * 	         <div th:object="${session.user}">
 * 	            <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
 * 	            <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
 * 	            <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
 *          </div>
 * 		Message Expressions: #{...}     (获取国际化功能)
 * 		Link URL Expressions: @{...}    (定义Url链接)
 * 	        @{/order/process(execId=${execId},execType='FAST')}
 * 		Fragment Expressions: ~{...}    (片段引用表达式)
 * 2 Literals  (字面量)
 * 		Text literals: 'one text' , 'Another one!' ,…
 * 		Number literals: 0 , 34 , 3.0 , 12.3 ,…
 * 		Boolean literals: true , false
 * 		Null literal: null
 * 		Literal tokens: one , sometext , main ,…
 * 3 Text operations: (文本操作)
 * 		String concatenation: +
 * 		Literal substitutions: |The name is ${name}|
 * 4 Arithmetic operations: (数学运算)
 * 		Binary operators: + , - , * , / , %
 * 		Minus sign (unary operator):
 * 5 Boolean operations:    (boolean运算)
 * 		Binary operators: and , or
 * 		Boolean negation (unary operator): ! , not
 * 6 Comparisons and equality: (比较运算)
 * 		Comparators: > , < , >= , <= ( gt , lt , ge , le )
 * 		Equality operators: == , != ( eq , ne )
 * 7 Conditional operators: (条件操作)
 * 		If-then: (if) ? (then)
 * 		If-then-else: (if) ? (then) : (else)
 * 		Default: (value) ?: (defaultvalue)
 * 8 Special tokens:    (特殊表示)
 * 		No-Operation: _
 * All these features can be combined and nested:
 * 		'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
 *
 * @Author zhoujie
 * @Date 2020/5/4 13:49
 * @Version 1.0
 **/
@Controller
public class ThymeleafController {
    @RequestMapping("thymeleaf/hello")
    public String hello(){
        return "success";
    }

    @RequestMapping("thymeleaf/hi")
    public String hi(Map<String,Object> map){
        map.put("hello","别客气");
        map.put("html","<p>你好</p>");
        map.put("users", Arrays.asList("张三","李四","王五","赵六"));
        Map rt = new HashMap();
        rt.put("name","joy");
        rt.put("age","23");
        rt.put("height",188);
        rt.put("sex",false);
        rt.put("birthday",new Date());
        map.put("user",rt);
        return "success";
    }
}
