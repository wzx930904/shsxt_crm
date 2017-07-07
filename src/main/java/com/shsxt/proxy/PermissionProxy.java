package com.shsxt.proxy;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.shsxt.base.AssertUtil;
import com.shsxt.base.Constant;
import com.shsxt.model.UserRole;
import com.shsxt.service.PermissioinService;
import com.shsxt.service.UserRoleService;
import com.shsxt.util.LoginUserUtil;

@Component
@Aspect
public class PermissionProxy {
	
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private PermissioinService permissionService;

	@Pointcut("execution (* com.shsxt.controller..*.*(..))")
	public void pointcut() {
		
	}
	
	@Around(value="pointcut()")
	//@Around(value="execution(* com.shsxt.controller..*.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {//pjp；连接点对象
		//用户是否登录
		Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
		String uri = request.getRequestURI();
		if ("/index".equals(uri) || "/user/login".equals(uri)) {//放行
			return pjp.proceed();
		}
		AssertUtil.intIsEmpty(userId, "请登录");
		//先从session查询是否存在用户的权限，如果存在就通过
		//List<String> permissions = (List<String>) request.getSession().getAttribute(Constant.USER_PERMISSION_SESSION_KEY);
		//if (permissions != null && !permissions.isEmpty()) {
		//	return pjp.proceed();
		//}
		//获取用户权限--》先获取角色 --》获取权限
		List<UserRole> userRoles = userRoleService.findUserRoles(userId);
		AssertUtil.isTrue(userRoles == null, "您无权访问此系统");
		String roleIds = "";
		for (UserRole userRole : userRoles) {
			roleIds += userRole.getRoleId() + ",";
		}
		List<String> permissions = permissionService.findRolePermissions(roleIds.substring(0, roleIds.lastIndexOf(",")));
		request.getSession().setAttribute("userPermissions", permissions);
		//执行代码
		Object result = pjp.proceed();
		return result;
	}
}
