package edu.sjsu.cmpe275.aop.aspect;


import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(2)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @return 
     * @throws Throwable 
     */

	@Around("execution(public * edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public Object dummyAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Retry aspect prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		for(int i = 0; i < 3; i++) {
			try {
				return joinPoint.proceed();
				//System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
			} catch (Throwable e) {
				if(e instanceof IOException)
					System.out.printf("Retrying the executuion of the metohd %s\n", joinPoint.getSignature().getName());
				else
					throw e;
			}
			
		}
		throw new IOException();
	}

}
