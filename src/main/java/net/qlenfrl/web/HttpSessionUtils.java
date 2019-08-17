package net.qlenfrl.web;

import javax.servlet.http.HttpSession;

import net.qlenfrl.domain.User;

public class HttpSessionUtils {
	public final static String USER_SESSION_KEY = "loginedUser";
	
	public static boolean isLoginUser(HttpSession session) {
		User loginedUser = (User) session.getAttribute(USER_SESSION_KEY);
		
		if (loginedUser == null) {
			return false;
		}
		
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}
