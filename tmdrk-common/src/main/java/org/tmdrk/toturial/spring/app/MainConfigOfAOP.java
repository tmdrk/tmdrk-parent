package org.tmdrk.toturial.spring.app;

import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.tmdrk.toturial.spring.aop.*;

/**
 * @ClassName MainConfigOfAOP
 * @Description
 * AOP
 *
 * 1 导入aop模块
 * 2 定义一个业务逻辑类MathCalculator
 * 3 定义一个日志切面类LogAspect
 *      通知方法
 *          前置通知(@Before) logStart
 *          后置通知(@After) logEnd 方法正常结束异常结束都会调用
 *          返回通知(@AfterReturning) logReturn
 *          异常通知(@AfterThrowing) logException
 *          环绕通知(@Around) 动态代理，手动推进目标方法运行（joinPoint.proceed()）
 * 4 给切面类的目标方法标注注解（通知注释）
 * 5 将业务逻辑类和切面类都加入到spring容器中
 * 6 必须要告诉spring容器哪个是切面类@Aspect
 * 7 给配置类加@EnableAspectJAutoProxy 开启基于注解的aop模式
 *
 * AOP原理【看加了什么注解，这个注解给容器中注册了什么组件，这个组件的功能是什么】
 *  @EnableAspectJAutoProxy
 *  1 @EnableAspectJAutoProxy是什么？
 *      @Import(AspectJAutoProxyRegistrar.class)
 *          利用AspectJAutoProxyRegistrar自定义容器中注册bean
 *          registry.registerBeanDefinition(internalAutoProxyCreator, AnnotationAwareAspectJAutoProxyCreator);
 *      给容器中注册一个 AnnotationAwareAspectJAutoProxyCreator bean
 *  2 AnnotationAwareAspectJAutoProxyCreator 继承结构及重要方法
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                      ->ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器在bean初始化完成前后所做的事情
 *                  setBeanFactory(BeanFactory beanFactory)
 *                  postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
 *                  postProcessAfterInstantiation(Object bean, String beanName)【此方法会创建动态代理类】
 *              setBeanFactory(BeanFactory beanFactory)
 *                  initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
 *      initBeanFactory(ConfigurableListableBeanFactory beanFactory)
 *  3 spring容器启动时是如何调用到AnnotationAwareAspectJAutoProxyCreator的方法的
 *		1 创建IOC容器
 * 		2 注册配置类，refresh();刷新方法
 * 		3 registerBeanPostProcessors(beanFactory); 注册bean后置处理器，方便拦截bean的创建
 * 			1 先获取所有bean后置处理器
 * 			String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 * 			2 给容器中加别的BeanPostProcessor
 * 			3 按优先级注册BeanPostProcessor【PriorityOrdered】
 * 			4 按优先级注册BeanPostProcessor【Ordered】
 * 				beanFactory.getBean(ppName, BeanPostProcessor.class);
 * 					doGetBean(name, requiredType, null, false)
 * 						getSingleton(beanName, () -> {})
 * 							singletonFactory.getObject();
 * 								doCreateBean(beanName, mbdToUse, args);【2】
 * 									(createBeanInstance(beanName, mbd, args);这里调用先创建了AnnotationAwareAspectjAutoProxyCreator对象;)
 * 									(applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);)允许后置处理器修改合并的beanDefinition
 * 									(populateBean(beanName, mbd, instanceWrapper);给bean属性赋值)
 * 									initializeBean(beanName, exposedObject, mbd) //初始化bean
 * 										(invokeAwareMethods(beanName, bean);处理Aware接口的方法回调)
 * 										(applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);应用后置处理器前初始化方法)
 * 										invokeInitMethods(beanName, wrappedBean, mbd);执行自定义的初始化方法 【如@Bean注解自定义初始化和销毁方法】
 * 											((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this)
 * 											也就是调用AbstractAdvisorAutoProxyCreator.setBeanFactory(BeanFactory beanFactory)
 * 											至此代码来到【自动代理创建器】
 * 												super.setBeanFactory(beanFactory);
 * 												initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
 * 													aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
 * 													aspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
 * 										(applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);应用后置处理器后初始化方法)
 * 			5 按优先级注册BeanPostProcessor【null】
 * =================== 以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程 ===================
 *		4 finishBeanFactoryInitialization(beanFactory);初始化所有还未被初始化的bean（获取所有beanDefinitionNames遍历创建）
 * 			beanFactory.preInstantiateSingletons();
 * 				getBean(beanName); 循环初始化所有还未被初始化的bean
 * 					doGetBean(name, null, null, false);
 * 						1 Object sharedInstance =getSingleton(beanName);从缓存中获取当前bean，获取不到则需要创建
 * 						2 sharedInstance = getSingleton(beanName, () -> {
 * 							try {
 * 								return createBean(beanName, mbd, args);【GOTO 1】
 * 							}
 * 							catch (BeansException ex) {
 * 								destroySingleton(beanName); throw ex;
 * 							}
 * 						});
 * 						3 createBean(beanName, mbd, args);【1】
 * 							1 resolveBeforeInstantiation(beanName, mbdToUse);给BeanPostProcessor一个返回目标bean代理类的机会
 * 								1 Object bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 									1 拿到所有后置处理器，循环处理，判断是否是 InstantiationAwareBeanPostProcessor 后置处理器
 * 									    【 AnnotationAwareAspectJAutoProxyCreator 后置处理器可以在任何bean创建之前创建bean代理实例，或给属性赋值】
 * 									2 如果是 Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName); //当为AnnotationAwareAspectJAutoProxyCreator类时
 * 								        1 if (this.advisedBeans.containsKey(cacheKey)) 	return null;// 校验advisedBeans是否已存在bean，是，则直接返回null
 * 								        2 if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)){
 * 								            //判断当前bean是否是基础类型的(Advice/Pointcut/Advisor/AopInfrastructureBean/是否是切面@Aspect),或是否需要跳过，若是存入advisedBeans，直接返回null
 * 								            this.advisedBeans.put(cacheKey, Boolean.FALSE);return null;
 * 								        }
 * 								            shouldSkip(beanClass, beanName) //详解该方法，因为该方法会初始化advisors
 * 								                //如果给定的bean不应该被该后置处理器考虑进行自动代理,子类应重写此方法以返回{@code true}。有时我们需要能够避免这种情况的发生，例如 是否将导致循环引用，或者是否需要保留现有的目标实例。
 * 								                //除非bean名称根据{@code AutowireCapableBeanFactory}约定指示“原始实例”，否则此实现将返回{@code false}。
 *                                              1 List<Advisor> candidateAdvisors = findCandidateAdvisors();//查找要在自动代理中使用的所有候选Advisor。
 *                                                  1 List<Advisor> advisors = super.findCandidateAdvisors();//调用父类AbstractAdvisorAutoProxyCreator方法，第一个调用一般是配置类，若其和它的父类不是切面类，则返回为空
 *                                                      return this.advisorRetrievalHelper.findAdvisorBeans();//在当前bean工厂中查找所有合格的Advisor bean，忽略FactoryBeans并排除当前正在创建的bean。
 *                                                          1 String[] advisorNames = this.cachedAdvisorBeanNames; //从缓存中获取advisorName
 *                                                          2 if(advisorNames==null) advisorNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.beanFactory, Advisor.class, true, false);
 *                                                          3 this.cachedAdvisorBeanNames = advisorNames;//advisorName存入缓存
 *                                                  2 if (this.aspectJAdvisorsBuilder != null) advisors.addAll(this.aspectJAdvisorsBuilder.buildAspectJAdvisors()); //若切面顾问构建器不为空，则构建切面顾问
 *                                                      aspectJAdvisorsBuilder.buildAspectJAdvisors()//详解该方法，在当前的bean工厂中查找带有AspectJ注释的Aspect bean，并返回代表它们的Spring AOP Advisor列表。
 *                                                          1 List<String> aspectNames = this.aspectBeanNames; //优先从aspectBeanNames缓存中获取，一般第一次创建bean为空
 *                                                          2 if (aspectNames == null) { //缓存为空则会遍历所有的beanName，获取Aspect注解切面bean，并赋给aspectBeanNames
 *                                                              List<Advisor> advisors = new ArrayList<>();
 *                                                              String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.beanFactory, Object.class, true, false);
 *                                                              for (String beanName : beanNames) {
 *                                                                  if (this.advisorFactory.isAspect(beanType)) {
 *                                                                      aspectNames.add(beanName);
 *                                                                      List<Advisor> classAdvisors = this.advisorFactory.getAdvisors(factory);//返回InstantiationModelAwarePointcutAdvisorImpl实例
 *                                                                      advisors.addAll(classAdvisors);
 *                                                              }
 *                                                          3 return advisors;
 *                                                  3 return advisors;
 *                                              2 for (Advisor advisor : candidateAdvisors) { //循环处理，判断
 * 			                                        if (advisor instanceof AspectJPointcutAdvisor && ((AspectJPointcutAdvisor) advisor).getAspectName().equals(beanName)) {
 * 				                                        return true;    }   } //如果给定的bean不应该被该后置处理器考虑进行自动代理,子类应重写此方法以返回true
 * 		                                        3 return super.shouldSkip(beanClass, beanName); //确定给定的bean名称是否指示“原始实例[original instance]”,是返回true，不是返回false
 *
 *                                      3 TargetSource targetSource = getCustomTargetSource(beanClass, beanName); //获取自定义TargetSource，若存在，则会创建代理类
 *                                      4 if (targetSource != null) {
 *                                          Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
 *                                          return proxy;
 *                                      }
 *                                      5 return null; //若获取自定义TargetSource不存在，则返回空，
 *                                 if (result != null) {return result;} //若不为空，则直接返回上一层方法
 *                                 return null; //表示所有后置处理器都没有返回代理类
 *                             2 if (bean != null) {bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);}//因为不用再实例化了，所以直接调用后初始化方法。
 *                             3 return bean;
 * 							2 if (bean != null) { //如果代理类返回不为空，则不走下面创建bean的逻辑，直接返回
 * 								return bean;
 * 							}
 * 							3 doCreateBean(beanName, mbdToUse, args);真正的创建bean实例【GOTO 2】
 * 							    1 createBeanInstance(beanName, mbd, args);【创建bean实例】
 * 							    2 applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);调用bean合并后置处理器
 * 								3 判断是否是单例，是否允许循环引用，是否正被创建，是则addSingletonFactory(beanName,singletonFactory)
 * 							    4 populateBean(beanName, mbd, instanceWrapper);【bean属性赋值】
 * 							    5 initializeBean(beanName, exposedObject, mbd);【bean初始化】
 * 							        1 invokeAwareMethods(beanName, bean); 执行xxxAware接口的方法(BeanNameAware BeanClassloaderAware BeanFactoryAware)
 * 							        2 wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);【执行后置处理器初始化之前方法】 所有BeanPostProcessors
 * 							        3 invokeInitMethods(beanName, wrappedBean, mbd);【执行初始化方法】
 * 								    4 wrappedBean =  applyBeanPostProcessorsAfterInitialization(bean, beanName);【执行后置处理器初始化之后方法】 所有BeanPostProcessors
 * 									    循环遍历所有bean后置处理器，processor.postProcessAfterInitialization(result, beanName);
 * 									    这里会调用【AnnotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization方法】
 * 									    1 Object cacheKey = getCacheKey(bean.getClass(), beanName);//为给定的Bean类和Bean名称构建一个缓存键
 * 										2 return wrapIfNecessary(bean, beanName, cacheKey);//如果需要的情况下包装bean
 * 									        1 if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) return bean; 校验advisedBeans是否已存在bean，是，则直接返回
 * 									        2 if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)){ //与上面applyBeanPostProcessorsBeforeInitialization时一样
 * 									            //判断当前bean是否是基础类型的(Advice/Pointcut/Advisor/AopInfrastructureBean/是否是切面@Aspect),或是否需要跳过，若是存入advisedBeans，直接返回bean
 * 									            this.advisedBeans.put(cacheKey, Boolean.FALSE); return bean;
 *                                          }
 * 											3 Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null); //获取当前bean的所有顾问(通知方法)，转换成拦截器
 * 										        1 List<Advisor> advisors = findEligibleAdvisors(beanClass, beanName); //获取合格的顾问
 * 										            1 List<Advisor> candidateAdvisors = findCandidateAdvisors();// 与上面applyBeanPostProcessorsBeforeInitialization时一样
 * 										            2 List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);//获取合格的Advisor顾问集合
 * 										                return AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
 * 										            3 extendAdvisors(eligibleAdvisors);//在advise链的开头添加一个{@link ExposeInvocationInterceptor}
 * 										            4 if (!eligibleAdvisors.isEmpty()) {eligibleAdvisors = sortAdvisors(eligibleAdvisors);}//非空，给增强器排序
 * 										            5 return eligibleAdvisors;
 * 										        2 if (advisors.isEmpty()) {return DO_NOT_PROXY;} //为空返回null
 * 										        3 return advisors.toArray();	//返回顾问数组
 * 											4 if (specificInterceptors != DO_NOT_PROXY) {
 * 										        this.advisedBeans.put(cacheKey, Boolean.TRUE);//specificInterceptors不为null，则存入advisedBeans中，cacheKey为类名，value表示是否需要代理
 * 										        Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));//创建代理类
 * 										            1 if (this.beanFactory instanceof ConfigurableListableBeanFactory) { //Expose the given target class for the specified bean, if possible.
 * 			                                            AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName, beanClass);//
 * 		                                            }
 * 										            2 ProxyFactory proxyFactory = new ProxyFactory(); //创建代理工场
 * 										            3 if (!proxyFactory.isProxyTargetClass()) { //判断是否设置proxyTargetClass属性，为true表示使用cglib动态代理
 * 			                                            if (shouldProxyTargetClass(beanClass, beanName)) {proxyFactory.setProxyTargetClass(true);}
 * 			                                            else {evaluateProxyInterfaces(beanClass, proxyFactory);} //没有设置，则根据是否实现接口初始化proxyTargetClass属性
 * 		                                            }
 * 		                                            4 Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);//构建顾问
 * 		                                            5 if (advisorsPreFiltered()) {proxyFactory.setPreFiltered(true);}  //设置属性
 * 		                                            6 return proxyFactory.getProxy(getProxyClassLoader());
 *                                                      1 return createAopProxy().getProxy(classLoader);
 *                                                          createAopProxy()//方法详解，创建AOP代理类
 *                                                              1 return getAopProxyFactory().createAopProxy(this);
 *                                                                  getAopProxyFactory()//获取代理工场DefaultAopProxyFactory
 *                                                                  createAopProxy(this)//方法详解，创建AOP代理类
 *                                                                      //是否优化，是否设置proxyTargetClass属性
 *                                                                      1 if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
 *                                                                          if (targetClass.isInterface()||Proxy.isProxyClass(targetClass)){return new JdkDynamicAopProxy(config);}
 *                                                                          return new ObjenesisCglibAopProxy(config); //若目标类不是接口，且不是代理类，则创建cglib代理类
 *                                                                      2 }else {return new JdkDynamicAopProxy(config);}
 *                                                          getProxy(classLoader)//方法详解，使用AOP代理类创建目标代理类，cglib生成代理过程如下
 *                                                              1 Enhancer enhancer = createEnhancer();
 *                                                              2 enhancer.setClassLoader(classLoader);
 *                                                              3 enhancer.setSuperclass(proxySuperClass);
 * 			                                                    4 enhancer.setInterfaces(AopProxyUtils.completeProxiedInterfaces(this.advised));
 * 			                                                    5 enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
 * 			                                                    6 enhancer.setStrategy(new ClassLoaderAwareUndeclaredThrowableStrategy(classLoader));
 * 			                                                    7 enhancer.setCallbackFilter(new ProxyCallbackFilter(this.advised.getConfigurationOnlyCopy(), this.fixedInterceptorMap, this.fixedInterceptorOffset));
 * 			                                                    8 enhancer.setCallbackTypes(types);
 * 			                                                    9 return createProxyClassAndInstance(enhancer, callbacks);//调用enhancer.create()的有参数或无参方法创建代理类
 * 										        this.proxyTypes.put(cacheKey, proxy.getClass());  return proxy;// proxyTypes存入key->类名，value->代理类Class
 * 										    }
 * 										    5 this.advisedBeans.put(cacheKey, Boolean.FALSE);  return bean;
 * 								6 registerDisposableBeanIfNecessary(beanName, bean, mbd);注册bean的销毁方法
 * 							4. 将创建的bean添加到缓存中 singletonObjects
 * =================== 以上是创建业务逻辑组件和切面组件的过程 ===================
 *
 * 目标方法的执行
 * 	mathCalculator.div(17,6);调用方法
 * 		CiglibAopProxy$DynamicAdvisedInterceptor[implements MethodInterceptor].intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) 执行拦截方法
 * 			1 List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass); advised(ProxyFactory)获取目标方法拦截器链
 * 				chain.size=5;
 * 				【ExposeInvacationInterceptor】【AspectJAfterThrowingAdvice】【AfterReturningAdviceInterceptor】【AspectJAfterAdvice】【MethodBeforeAdviceInterceptor】
 * 				1 this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass)优先从缓存中取值，没有则调用方法;
 * 					1 List<Object> interceptorList = new ArrayList<>(advisors.length);创建list用来保存所有拦截器
 * 					advisors.length包括1个默认的ExposeInvocationInterceptor和4个增强器(包含类名，方法名，pointcut(),参数等信息)
 * 					2 遍历所有的增强器，将其转为Interceptor
 * 					3 if (advisor instanceof PointcutAdvisor) //主要走PointcutAdvisor
 * 						mm.matches(method, actualClass);是否方法与pointcut()匹配
 * 						MethodInterceptor[] interceptors = registry.getInterceptors(advisor);若匹配，则根据advisor获取所有拦截器
 * 							1 if (advice instanceof MethodInterceptor) 若是MethodInterceptor类型，直接放入interceptors中
 * 							2 for (AdvisorAdapter adapter : this.adapters) 遍历adapters，包含[MethodBefore、AfterReturning、Throws]AdviceAdapter
 * 								adapter.supportsAdvice(advice) = true 则 interceptors.add(adapter.getInterceptor(advisor));
 * 							3 return interceptors
 * 						interceptorList.addAll(Arrays.asList(interceptors));加入到interceptorList
 * 					4 else if (advisor instanceof IntroductionAdvisor)
 * 						if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
 * 							Interceptor[] interceptors = registry.getInterceptors(advisor);
 * 							interceptorList.addAll(Arrays.asList(interceptors));
 * 						}
 * 					5 else
 * 						Interceptor[] interceptors = registry.getInterceptors(advisor);
 * 						interceptorList.addAll(Arrays.asList(interceptors));
 * 			2 如果为空，直接调用目标方法 methodProxy.invoke(target, argsToUse);
 * 			2 若不为空，创建对象 new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy);
 * 			3 调用proceed方法 Object retVal = CglibMethodInvocation.proceed() 【拦截器链的触发过程】【3】
 * 				1 如果没有拦截器，直接通过反射调用原方法 invokeJoinpoint();
 * 					AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
 * 						method.invoke(target, args);
 * 				2 否则获取一个拦截器 methodInterceptor_0(ExposeInvacationInterceptor) = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex)
 * 				3 if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
 * 					if (dm.methodMatcher.matches(this.method, targetClass, this.arguments))
 * 						return dm.interceptor.invoke(this);
 * 					else
 * 						return proceed();
 * 				4 else //这里增强方法都是利用反射调用的，先method.setAccessible(true);再method.invoke();
 * 					return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
 * 						1 return methodInterceptor_0[ExposeInvacationInterceptor].invoke(this);
 * 							1 invocation.set(mi);
 * 							2 return mi.proceed();【GOTO 3】
 * 								return methodInterceptor_1[AspectJAfterThrowingAdvice].invoke(this)
 * 									try { return mi.proceed();}【GOTO 3】
 * 										return methodInterceptor_2[AfterReturningAdviceInterceptor].invoke(this);
 * 											1 Object retVal = mi.proceed();【GOTO 3】
 * 												return methodInterceptor_3[AspectJAfterAdvice].invoke(this);
 * 													1 try {return mi.proceed();}【GOTO 3】
 * 														return methodInterceptor_4[MethodBeforeAdviceInterceptor].invoke(this);
 * 															1 this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());【执行before方法】
 * 															2 return mi.proceed();【GOTO 3】
 * 																(this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1)==true
 * 																return invokeJoinpoint(); 【执行原方法】
 * 													2 finally {invokeAdviceMethod(getJoinPointMatch(), null, null);}【执行after方法】
 * 											2 this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());【执行afterReturning方法】
 * 											3 return retVal;
 * 									catch (Throwable ex) {invokeAdviceMethod(getJoinPointMatch(), null, ex);}【执行afterThrowing方法】
 * 							3 finally {invocation.set(oldInvocation);}
 * 			4 return retVal;
 *=============================== 以上是执行目标方法的过程 ================================
 *
 *
 * =============================== AOP总结 ================================
 * 	1 @EnableAspectJAutoProxy开启AOP功能
 * 	2 @EnableAspectJAutoProxy会给容器import一个AspectJAutoProxyRegistrar.class
 * 	3 AspectJAutoProxyRegistrar会给容器注册一个AnnotationAwareAspectJAutoProxyCreator bean后置处理器
 * 	4 容器创建流程
 * 		1 invokeBeanFactoryPostProcessors(beanFactory);import导入 AnnotationAwareAspectJAutoProxyCreator 的beanDefination
 * 		2 registerBeanPostProcessors(beanFactory);注册bean后置处理器，创建 AnnotationAwareAspectJAutoProxyCreator 对象
 * 		3 初始化剩下的单实例bean
 * 			1 创建业务逻辑组件和切面组件
 * 			2 AnnotationAwareAspectJAutoProxyCreator 拦截组件的创建过程
 * 			3 组件创建完成之后，AnnotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization() 判断组件是否需要增强
 * 				是切面的通知方法，包装成增强器(Advisor);给 业务逻辑组件创建一个代理对象(没接口cglib)
 * 	5 执行容器的目标方法
 * 		1 代理对象执行目标方法
 * 		2 CiglibAopProxy.intercept()
 * 			1 得到目标方法的拦截器链(增强器包装成拦截器MethodInterceptor)
 * 			2 利用拦截器的链式机制，依次进入每一个拦截器进行执行
 * 			3 效果
 * 				1 正常执行 【前置通知->目标方法->后置通知->后置返回通知】
 * 				2 异常执行 【前置通知->目标方法->后置通知->后置异常通知】
 *
 * @Author zhoujie
 * @Date 2020/1/14 19:17
 * @Version 1.0
 **/
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {
    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }
    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
    @Bean
    public SmsAspect smsAspect(){
        return new SmsAspect();
    }
    @Bean
    public MathCalculatorTargetSource mathCalculatorTargetSource(){
    return new MathCalculatorTargetSource();
}
}
