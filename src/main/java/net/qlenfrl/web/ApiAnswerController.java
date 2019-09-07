package net.qlenfrl.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.qlenfrl.domain.Answer;
import net.qlenfrl.domain.AnswerRepository;
import net.qlenfrl.domain.Question;
import net.qlenfrl.domain.QuestionRepository;
import net.qlenfrl.domain.Result;
import net.qlenfrl.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public Answer create(HttpSession session, @PathVariable Long questionId, String contents) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginedUser, question, contents);
		
		question.addAnswer();
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{answerId}")
	public Result delete(HttpSession session, @PathVariable Long answerId, @PathVariable Long questionId) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail(Result.LOGIN_ERROR);
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findById(answerId).get();
		
		if (!answer.isSameWriter(loginedUser)) {
			return Result.fail(Result.USER_ERROR);
		}
		
		Question question = questionRepository.findById(questionId).get();
		
		question.removeAnswer();
		answerRepository.deleteById(answerId);
		return Result.ok();
	}
	
	@GetMapping("/{answerId}")
	public Answer updateForm(HttpSession session, @PathVariable Long answerId) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findById(answerId).get();
		
		if (!answer.isSameWriter(loginedUser)) {
			return null;
		}
		
		return answerRepository.findById(answerId).get();
	}
	
	@PutMapping("/{answerId}")
	public Answer updateAnswer(HttpSession session, @PathVariable Long answerId, String contents) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findById(answerId).get();
		
		if (!answer.isSameWriter(loginedUser)) {
			return null;
		}
		
		answer.update(contents);
		return answerRepository.save(answer);
	}
}
