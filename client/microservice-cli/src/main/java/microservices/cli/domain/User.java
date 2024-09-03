package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class User {

	private Long id;
	private String username;
	private Set<Video> postedVideos;
	private Set<Video> viewedVideos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Video> getPostedVideos() {
		return postedVideos;
	}

	public void setPostedVideos(Set<Video> postedVideos) {
		this.postedVideos = postedVideos;
	}

	public Set<Video> getViewedVideos() {
		return viewedVideos;
	}

	public void setViewedVideos(Set<Video> viewedVideos) {
		this.viewedVideos = viewedVideos;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

}
