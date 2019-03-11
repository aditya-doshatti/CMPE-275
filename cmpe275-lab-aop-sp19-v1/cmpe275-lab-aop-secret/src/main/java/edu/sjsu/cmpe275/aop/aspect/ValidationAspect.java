package edu.sjsu.cmpe275.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(-1)
public class ValidationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public void beforeAll(JoinPoint joinPoint) {
		System.out.printf("Doing validation prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		for(Object obj:joinPoint.getArgs())
			if(obj == null)
				throw new IllegalArgumentException();
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.createSecret(..))")
	public void beforeCreateSecret(JoinPoint joinPoint) {
		String secret = joinPoint.getArgs()[1].toString();
		if(secret.length() > 100)
			throw new IllegalArgumentException();
	}

}
