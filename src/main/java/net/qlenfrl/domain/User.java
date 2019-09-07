package net.qlenfrl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {
	@JsonProperty
	@Column(length=20, nullable=false)
	private String userId;
	
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean matchId(Long id) {
		if (id == null) {
			return false;
		}
		
		return this.getId().equals(id);
	}

	public boolean isSamePassword(String password) {
		if (this.password.equals(password)) {
			return true;
		}

		return false;
	}

	public void update(User updatedUser) {
		userId = updatedUser.userId;
		password = updatedUser.password;
		name = updatedUser.name;
		email = updatedUser.email;
	}

}
