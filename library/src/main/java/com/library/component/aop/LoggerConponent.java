package com.library.component.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//注释掉@Aspect方便开发看控制台，注释掉后aop切面不起作用，因为这个注解表明这是一个aop切面类，实际生产中要打开
@Aspect
@Component
public class LoggerConponent {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(* com.library.controller..*(..))")
	public void point() {

	}

	/*
	 * 四合一通知就是环绕通知 它有一个参数
	 */
	@Around("point()")
	public Object around(ProceedingJoinPoint pjb) {
		// 方法的参数
		Object[] args = pjb.getArgs();

		// 方法名
		String methodName = pjb.getSignature().getName();
		System.err.print(pjb.getTarget().getClass());
		;
		Object result = null;
		// proceed:利用反射调用目标方法，等价于method.invoke(obj,args);
		try {

			// System.out.println("前置通知：" + "[" + methodName + "]:方法开始执行了，参数是:"
			// + Arrays.asList(args));
			// 这句话就是相当于method.invoke(obj,args);就是动态代理帮我们调用目标方法
			// 如果没有这句话，相当于这个方法没有调用目标方法，就会报错
			result = pjb.proceed(args);
			// System.out.println("返回通知：[" + methodName + "]:方法执行成功，结果是" +
			// result);

		} catch (Throwable e) {
			logger.error("****************************************************");
			logger.error("在" + pjb.getTarget().getClass() + "中出现异常了");
			logger.error("前置通知：" + "[" + methodName + "]:方法参数是:" + Arrays.asList(args));
			logger.error("异常通知:[" + methodName + "]:方法出现异常，异常信息是");
			logger.error("\n");

			logger.error(e.getMessage());
			System.out.println(e.getMessage());
			logger.error("\n");
			logger.error("****************************************************");
			throw new RuntimeException(e);
			// 普通通知和环绕通知同时存在的情况下，异常会把被环绕通知的catch块捕//获，为了普通通知也能感受到异常，所以需要手动抛出
			// 如果不手动抛出异常，那么异常就不会被全局异常处理捕获到，因为它在这里已经被捕获了，为了全局异常也可以捕获到这个异常，我们收到将他抛出
		} finally {
			System.out.println("后置通知:[" + methodName + "]:方法结束");
		}
		// 结果一定要返回出去，它是方法执行的结果
		return result;
	}

}
