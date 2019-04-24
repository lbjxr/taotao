package guo.ping.taotao.portal.interceptor;

import guo.ping.taotao.common.utils.CookieUtils;
import guo.ping.taotao.pojo.TbUser;
import guo.ping.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		//在Handler之前处理
		//从cookie获取用户的token
		String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKRN");
		TbUser user = userService.getUserByToken(token);
		//用户信息为空
		if (null == user){
			httpServletResponse.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN +
					"?redirect=" + httpServletRequest.getRequestURL());
			return false;
		}
		//返回值决定handler是否执行，true：执行，falser :不执行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		//handler执行之后，返回ModelAndView之前
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		//返回ModelAndView之后执行
	}
}
