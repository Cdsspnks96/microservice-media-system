package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class SubVideo {

	private Long id;
	private Long vmid;
	private String title;
	private Long likes;
	private Set<SubHashtag> hashtags;
	private Set<SubUser> viewers; 
	
	// ID LOGIC
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getVmid() {
		return vmid;
	}

	public void setVmd(Long id) {
		this.vmid = id;
	}
	
	// Title Logic
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	// Likes Logic
	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}
	
	// Video hash-tags
	public Set<SubHashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<SubHashtag> hashtags) {
		this.hashtags = hashtags;
	}
	
	// Video viewers
	public Set<SubUser> getViewers() {
		return viewers;
	}
	
	public void setViewers(Set<SubUser> viewers) {
		this.viewers = viewers;
	}
	
	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", likes=" + likes + "]";
	}
}