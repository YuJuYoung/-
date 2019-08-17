package net.qlenfrl.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User writer;
	
	@OneToMany(mappedBy = "question")
	@OrderBy("id DESC")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Answer> answers;

	@Lob
	@JsonProperty
	private String contents;
    
	@JsonProperty
	private String title;
	
	@JsonProperty
	private Integer countOfAnswer = 0;

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}

	public String getTitle() {
		return title;
	}

	public Question() {

	}

	public Question(User writer, String contents, String title) {
		this.writer = writer;
		this.contents = contents;
		this.title = title;
	}

	public void update(String contents, String title) {
		this.contents = contents;
		this.title = title;
	}

	public boolean isSameWriter(User loginedUser) {
		return writer.equals(loginedUser);
	}
	
	public void addAnswer() {
		countOfAnswer++;
	}
	
	public void removeAnswer() {
		countOfAnswer--;
	}

}
