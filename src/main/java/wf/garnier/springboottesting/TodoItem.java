package wf.garnier.springboottesting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
final class TodoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String text;

	TodoItem(Long id, String text) {
		this.id = id;
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

}
