package microservices.video.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Video> postedVideos;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "viewers", fetch = FetchType.EAGER)
	private Set<Video> viewedVideos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "opinionUser", fetch = FetchType.EAGER)
	private Set<Opinion> opinions;
	
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

	// User posts
	public Set<Video> getPostedVideos() {
		return postedVideos;
	}

	public void setPostedVideos(Set<Video> postedVideos) {
		this.postedVideos = postedVideos;
	}
	
	// User history
	public Set<Video> getViewedVideos() {
		return viewedVideos;
	}

	public void setViewedVideos(Set<Video> viewedVideos) {
		this.viewedVideos = viewedVideos;
	}
	
	// User opinions
	public Set<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(Set<Opinion> opinions) {
		this.opinions = opinions;
	}
}
