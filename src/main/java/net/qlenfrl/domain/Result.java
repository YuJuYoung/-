package net.qlenfrl.domain;

public class Result {
	public final static String LOGIN_ERROR = "로그인이 필요합니다";
	public final static String USER_ERROR = "자신의 것만 가능합니다";
	public final static String PASSWORD_ERROR = "비밀번호가 틀렸습니다";
	
	private boolean valid;
	private String errorMessage;
	
	private Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
