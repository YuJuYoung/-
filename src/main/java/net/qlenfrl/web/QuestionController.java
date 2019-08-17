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

import net.qlenfrl.domain.Question;
import net.qlenfrl.domain.QuestionRepository;
import net.qlenfrl.domain.Result;
import net.qlenfrl.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "user/login";
		}
		
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(HttpSession session, String contents, String title) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "user/login";
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(loginedUser, contents, title);
		
		questionRepository.save(newQuestion);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		model.addAttribute("question", question);
		return "qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String contents, String title, HttpSession session, Model model) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		question.update(contents, title);
		questionRepository.save(question);
		return "redirect:/questions/{id}";
	}
	
	@DeleteMapping("/{id}")
	public String delete(HttpSession session, @PathVariable Long id, Model model) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "user/login";
		}
		
		questionRepository.delete(question);
		return "redirect:/";
	}
	
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail(Result.LOGIN_ERROR);
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		
		if (!question.isSameWriter(loginedUser)) {
			return Result.fail(Result.USER_ERROR);
		}
		
		return Result.ok();
	}
}
