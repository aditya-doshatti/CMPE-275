package edu.sjsu.cmpe275.aop.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.SecretStatsImpl;

@Aspect
@Order(1)
public class AccessControlAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	@Autowired SecretStatsImpl stats;

//	@Before("execution(public void edu.sjsu.cmpe275.aop.SecretService.*(..))")
//	public void dummyAdvice(JoinPoint joinPoint) {
//		System.out.printf("Access control prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
//	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.readSecret(..))")
	public void beforeReadSecret(JoinPoint joinPoint) {
		 stats.canUserReadIt(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1]);
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.shareSecret(..))")
	public void beforeShareSecret(JoinPoint joinPoint) {
		 stats.canUserReadIt(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1]);
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.unshareSecret(..))")
	public void beforeunShareSecret(JoinPoint joinPoint) {
		 stats.hasUserCreatedIt(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1]);
	}

}
