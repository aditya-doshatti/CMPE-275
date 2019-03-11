package edu.sjsu.cmpe275.aop.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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

	@AfterReturning(pointcut="execution(public * edu.sjsu.cmpe275.aop.SecretService.createSecret(..))", returning="retVal")
	public void afterCreateSecret(JoinPoint joinPoint, UUID retVal) {
		stats.recordSecretCreation(joinPoint.getArgs()[0].toString(), retVal, joinPoint.getArgs()[1].toString());
		stats.storeLengthOfLongest(joinPoint.getArgs()[1].toString());
	}
	
	@After("execution(public * edu.sjsu.cmpe275.aop.SecretService.shareSecret(..))")
	public void afterShareSecret(JoinPoint joinPoint) {
		stats.recordSecretShare(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1], joinPoint.getArgs()[2].toString());
	}
	
	@After("execution(public * edu.sjsu.cmpe275.aop.SecretService.unshareSecret(..))")
	public void afterUnshareSecret(JoinPoint joinPoint) {
		stats.recordSecretUnshare(joinPoint.getArgs()[0].toString(), (UUID) joinPoint.getArgs()[1], joinPoint.getArgs()[2].toString());
	}
	
	@After("execution(public * edu.sjsu.cmpe275.aop.SecretService.readSecret(..))")
	public void afterReadSecret(JoinPoint joinPoint) {
		 stats.recordReadOfSecret((UUID) joinPoint.getArgs()[1]);
	}

}
