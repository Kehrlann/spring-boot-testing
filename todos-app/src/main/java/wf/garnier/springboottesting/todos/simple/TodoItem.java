package wf.garnier.springboottesting.todos.simple;

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

	private String text;

	private String description;

	TodoItem(String text, String description) {
		this.text = text;
		this.description = description;
	}

	public TodoItem() {

	}

	public Long id() {
		return id;
	}

	public String text() {
		return text;
	}

	public String description() {
		return this.description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TodoItem todoItem = (TodoItem) o;
		return Objects.equals(id, todoItem.id) && Objects.equals(text, todoItem.text)
				&& Objects.equals(description, todoItem.description);
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(id);
		result = 31 * result + Objects.hashCode(text);
		result = 31 * result + Objects.hashCode(description);
		return result;
	}

}
