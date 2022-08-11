package com.model2.mvc.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

//XML에 선언적으로 aspect의 적용
public class LogAspectJ {
	
	public LogAspectJ() {
		System.out.println("::LogAspectJ Default Constructor");
	}
	
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("\n==========================================================");
		System.out.println("[Around before]" + getClass() + ".invoke() start...");
		System.out.println("[Around before] 타겟 객체 : " + joinPoint.getTarget().getClass().getName());
		System.out.println("[Around before] 타겟 객체의 호출 될 method : " + joinPoint.getSignature().getName());
		if(joinPoint.getArgs().length != 0) {
			System.out.println("Around before] 타겟 객체의 호출할 method에 전달되는 인자 : " + joinPoint.getArgs()[0]);
		}
		System.out.println("==========================================================");
		
		//타겟 객체의 Method를 호출하는 부분
		Object obj = joinPoint.proceed();

		System.out.println("==========================================================");
		System.out.println("[Around after] 타켓 객체의 호출 후 return value : " + obj);
		System.out.println("[Around after]" + getClass() + ".invoke() end...");
		System.out.println("==========================================================\n");
		return obj;
	}

}
