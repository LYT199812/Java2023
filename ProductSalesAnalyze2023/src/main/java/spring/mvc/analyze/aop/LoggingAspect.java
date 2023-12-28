package spring.mvc.analyze.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// springmvc-servlet.xml 有定義以下資訊，這裡就可以不用寫@Aspect、@Component、@Before，或是這裡定義，xml就不用定義
/*
 *  <!-- 定義 LoggingAspect 切面 -->
    <bean id="loggingAspect" class="spring.mvc.group_buy.aop.LoggingAspect"/>
    
    <!-- 定義切面和通知 -->
    <aop:config>
        <aop:aspect id="myAspect" ref="loggingAspect">
            <aop:pointcut id="logMethod"
                          expression="execution(* spring.mvc.group_buy.controller.*.*(..))"/>
            <aop:before pointcut-ref="logMethod" method="logMethodParams"/>
        </aop:aspect>
    </aop:config>
 */
@Aspect
@Component
public class LoggingAspect {

	// 監聽網頁前端傳給spring的資料，就可以知道網頁傳過來的參數對不對
	@Before("execution(* spring.mvc.analyze.controller.*.*(..))")
	public void logMethodParams(JoinPoint joinPoint) { //方法名要跟定義的一樣，因為我們有定義，除非在xml不要定義
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		System.out.printf("調用方法: %s 參數: %s%n", methodName, Arrays.toString(args));
	}
}
