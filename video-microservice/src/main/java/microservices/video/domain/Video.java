package microservices.video.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Video {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String title;

	@ManyToOne(optional = false)
	private User user; //author
	
	@JsonIgnore
	@ManyToMany
	private Set<Hashtag> hashtags;
	
	@JsonIgnore
	@ManyToMany
	private Set<User> viewers;
	
	@JsonIgnore
	@OneToMany(mappedBy = "videoOpinion", fetch = FetchType.EAGER)
	private Set<Opinion> opinions;
	
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
	
	// Video hash-tags
	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	
	// Video viewers
	public Set<User> getViewers() {
		return viewers;
	}
	
	public void setViewers(Set<User> viewers) {
		this.viewers = viewers;
	}
	
	// Video opinions
	public Set<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(Set<Opinion> opinions) {
		this.opinions = opinions;
	}
}