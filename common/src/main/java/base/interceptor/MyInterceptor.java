package base.interceptor;

import base.constant.RedisKeyPrefixConst;
import base.session.SessionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义拦截器
 *
 */
@Slf4j
public class MyInterceptor  implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		// System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)throws Exception {
		// System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//查看全局session
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		if (url.indexOf("/v1")>-1||url.indexOf("/wx")>-1) {// 如果要访问的资源是不需要验证的
			return true;
		}
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(RedisKeyPrefixConst.SESSION_INFO);
		if ((sessionInfo == null) || (sessionInfo.getLoginId() == null)) {// 如果没有登录或登录超时
			response.sendRedirect(request.getContextPath()+"/error/404");
			return false;
		}
		return true;// 只有返回true才会继续向下执行，返回false取消当前请求
	}

}
