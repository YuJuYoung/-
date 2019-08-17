package net.qlenfrl.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.qlenfrl.domain.Result;
import net.qlenfrl.domain.User;
import net.qlenfrl.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/form")
	public String form() {
		return "user/form";
	}
	
	@PostMapping("")
	public String create(User user, Model model) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model, HttpSession session) {
		model.addAttribute("user", userRepository.findAll());
		return "user/list";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			return "user/login";
		}
		
		if (!user.isSamePassword(password)) {
			return "user/login";
		}
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(HttpSession session, @PathVariable Long id, Model model) {
		Result result = valid(session, id);
		
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		model.addAttribute("user", userRepository.findById(id).get());
		return "user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session, Model model) {
		Result result = valid(session, id);
		
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session, Model model, String password) {
		Result result = valid(session, id);
		
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		
		if (!loginedUser.isSamePassword(password)) {
			model.addAttribute("errorMessage", Result.PASSWORD_ERROR);
			return "user/deleteForm";
		}
		
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		userRepository.deleteById(id);
		return "redirect:/users";
	}
	
	@GetMapping("/{id}/delete")
	public String deleteForm(@PathVariable Long id, HttpSession session, Model model) {
		Result result = valid(session, id);
		
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		return "user/deleteForm";
	}
	
	private Result valid(HttpSession session, Long id) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail(Result.LOGIN_ERROR);
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		
		if (!loginedUser.matchId(id)) {
			return Result.fail(Result.USER_ERROR);
		}
		
		return Result.ok();
	}
}
