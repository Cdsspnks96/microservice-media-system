package microservices.cli.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Video {

	private Long id;
	private String title;
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", user=" + user.getUsername() + "]";
	}
}