package edu.sjsu.cmpe275.aop.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.SecretStatsImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired SecretStatsImpl stats;
	
	@After("execution(public void edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public void dummyAfterAdvice(JoinPoint joinPoint) {
		System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		//stats.resetStats();
	}
	
	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.SecretService.createSecret(..))", returning="retVal")
	public void afterCreateSecret(JoinPoint joinPoint, UUID retVal) {
		stats.recordSecretCreation(joinPoint.getArgs()[0].toString(), retVal);
	}
	
	@After("execution(public * edu.sjsu.cmpe275.aop.SecretService.shareSecret(..))")
	public void afterShareSecret(JoinPoint joinPoint) {
		stats.recordSecretShare(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1], joinPoint.getArgs()[2].toString());
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public void dummyBeforeAdvice(JoinPoint joinPoint) {
		System.out.printf("Doing stats before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}
	
}
