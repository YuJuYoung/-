package net.qlenfrl.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.qlenfrl.domain.Result;
import net.qlenfrl.domain.User;
import net.qlenfrl.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long id, HttpSession session, String password) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail(Result.LOGIN_ERROR);
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		
		if (!loginedUser.isSamePassword(password)) {
			return Result.fail(Result.PASSWORD_ERROR);
		}
		
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		userRepository.deleteById(id);
		return Result.ok();
	}
	
	@PostMapping("/login")
	public Result login(String userId, String password, HttpSession session) {
		if (userId.equals("") || password.equals("")) {
			return Result.fail(Result.INPUT_EMPTY);
		}
		
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			return Result.fail(Result.ID_ERROR);
		}
		
		if (!user.isSamePassword(password)) {
			return Result.fail(Result.PASSWORD_ERROR);
		}
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return Result.ok();
	}
	
}
