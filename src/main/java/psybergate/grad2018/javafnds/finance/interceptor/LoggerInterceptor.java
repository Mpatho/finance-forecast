package psybergate.grad2018.javafnds.finance.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LoggerInterceptor {

	@AroundInvoke
	public Object logMethodInvocation(InvocationContext invocationContext)
			throws Exception {
		System.out.println("Entering method: "
				+ invocationContext.getMethod().getName() + " in class "
				+ invocationContext.getMethod().getDeclaringClass().getSimpleName());

		Object obj = invocationContext.proceed();

		System.out.println("Exiting method: "
				+ invocationContext.getMethod().getName() + " in class "
				+ invocationContext.getMethod().getDeclaringClass().getSimpleName());

		return obj;
	}
}
