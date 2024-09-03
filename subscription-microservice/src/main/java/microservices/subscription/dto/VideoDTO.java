package microservices.subscription.dto;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class VideoDTO {

	private Long id;

	private String title;

	private UserDTO user; //author
	
	private Set<HashtagDTO> hashtags;
	
	private Set<UserDTO> viewers;
	
	private Set<OpinionDTO> opinions;
	
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	// Video hash-tags
	public Set<HashtagDTO> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<HashtagDTO> hashtags) {
		this.hashtags = hashtags;
	}
	
	// Video viewers
	public Set<UserDTO> getViewers() {
		return viewers;
	}
	
	public void setViewers(Set<UserDTO> viewers) {
		this.viewers = viewers;
	}
	
	// Video opinions
	public Set<OpinionDTO> getOpinions() {
		return opinions;
	}

	public void setOpinions(Set<OpinionDTO> opinions) {
		this.opinions = opinions;
	}
}