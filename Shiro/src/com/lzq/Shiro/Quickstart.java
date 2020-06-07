package com.lzq.Shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

	private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

	public static void main(String[] args) {

		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();

		SecurityUtils.setSecurityManager(securityManager);

		// 获取Subject,应用代码直接交互的对象
		Subject currentUser = SecurityUtils.getSubject();

		// 测试使用Session
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			log.info("这个Session存在对应值 ---->  Retrieved the correct value! [" + value + "]");
		}

		// 测试当前用户是否认证,即是否登录
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码进行封装成一个对象
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			try {
				// 执行登录
				currentUser.login(token);
				// 没有指定的对象,抛出异常
			} catch (UnknownAccountException uae) {
				log.info("账号不存在 --->  There is no user with username of " + token.getPrincipal());
				return;
			} catch (IncorrectCredentialsException ice) { // 密码不正确异常
				log.info("密码错误 ----> Password for account " + token.getPrincipal() + " was incorrect!");
				return;
			} catch (LockedAccountException lae) {// 用户锁定异常
				log.info("The account for username " + token.getPrincipal() + " is locked.  "
						+ "Please contact your administrator to unlock it.");
			}

			catch (AuthenticationException ae) {

			}
		}
		// print their identifying principal (in this case, a username):
		log.info("登录成功  - - ->  User [" + currentUser.getPrincipal() + "] logged in successfully.");

		// 测试是否有这么一个角色
		if (currentUser.hasRole("schwartz")) {
			log.info("存在角色 --->May the Schwartz be with you!");
		} else {
			log.info("角色不存在 --->Hello, mere mortal.");
		}

		// 测试用户是否拥有权限
		if (currentUser.isPermitted("lightsaber:wield")) {
			log.info("有权限  - - - > You may use a lightsaber ring.  Use it wisely.");
		} else {
			log.info("没有权限  - - - > Sorry, lightsaber rings are for schwartz masters only.");
		}

		// 测试权限,更加具体
		if (currentUser.isPermitted("winnebago:drive:eagle5")) {
			log.info("具体行为的权限 - - ->You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  "
					+ "Here are the keys - have fun!");
		} else {
			log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		}

		log.info("状态 ： " + currentUser.isAuthenticated());
		// 退出登录
		currentUser.logout();
		log.info("状态 ： " + currentUser.isAuthenticated());
		System.exit(0);
	}
}
