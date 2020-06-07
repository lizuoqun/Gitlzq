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

		// ��ȡSubject,Ӧ�ô���ֱ�ӽ����Ķ���
		Subject currentUser = SecurityUtils.getSubject();

		// ����ʹ��Session
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			log.info("���Session���ڶ�Ӧֵ ---->  Retrieved the correct value! [" + value + "]");
		}

		// ���Ե�ǰ�û��Ƿ���֤,���Ƿ��¼
		if (!currentUser.isAuthenticated()) {
			// ���û�����������з�װ��һ������
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			try {
				// ִ�е�¼
				currentUser.login(token);
				// û��ָ���Ķ���,�׳��쳣
			} catch (UnknownAccountException uae) {
				log.info("�˺Ų����� --->  There is no user with username of " + token.getPrincipal());
				return;
			} catch (IncorrectCredentialsException ice) { // ���벻��ȷ�쳣
				log.info("������� ----> Password for account " + token.getPrincipal() + " was incorrect!");
				return;
			} catch (LockedAccountException lae) {// �û������쳣
				log.info("The account for username " + token.getPrincipal() + " is locked.  "
						+ "Please contact your administrator to unlock it.");
			}

			catch (AuthenticationException ae) {

			}
		}
		// print their identifying principal (in this case, a username):
		log.info("��¼�ɹ�  - - ->  User [" + currentUser.getPrincipal() + "] logged in successfully.");

		// �����Ƿ�����ôһ����ɫ
		if (currentUser.hasRole("schwartz")) {
			log.info("���ڽ�ɫ --->May the Schwartz be with you!");
		} else {
			log.info("��ɫ������ --->Hello, mere mortal.");
		}

		// �����û��Ƿ�ӵ��Ȩ��
		if (currentUser.isPermitted("lightsaber:wield")) {
			log.info("��Ȩ��  - - - > You may use a lightsaber ring.  Use it wisely.");
		} else {
			log.info("û��Ȩ��  - - - > Sorry, lightsaber rings are for schwartz masters only.");
		}

		// ����Ȩ��,���Ӿ���
		if (currentUser.isPermitted("winnebago:drive:eagle5")) {
			log.info("������Ϊ��Ȩ�� - - ->You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  "
					+ "Here are the keys - have fun!");
		} else {
			log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		}

		log.info("״̬ �� " + currentUser.isAuthenticated());
		// �˳���¼
		currentUser.logout();
		log.info("״̬ �� " + currentUser.isAuthenticated());
		System.exit(0);
	}
}
