package com.shsxt.proxy;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.shsxt.annotation.SystemLog;

@Component
@Aspect
public class SystemLogProxy {

	@Pointcut("@annotation(com.shsxt.annotation.SystemLog)")
	public void pointcut() {
		
	}
	
	@Around(value="pointcut()&&@annotation(systemLog)")
	public Object aroundMethod(ProceedingJoinPoint pjp,SystemLog systemLog) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		System.out.println(method.getName());
		System.out.println(pjp);
		return pjp.proceed();
	}
}
