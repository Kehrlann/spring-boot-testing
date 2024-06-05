package wf.garnier.springboottesting.todos.users;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
final class TodoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String text;

	TodoItem(Long id, String text, String username) {
		this.id = id;
		this.username = username;
		this.text = text;
	}

	public TodoItem() {

	}

	public Long id() {
		return id;
	}

	public String text() {
		return text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TodoItem todoItem = (TodoItem) o;

		if (!Objects.equals(id, todoItem.id))
			return false;
		if (!Objects.equals(username, todoItem.username))
			return false;
		return Objects.equals(text, todoItem.text);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		return result;
	}

}
