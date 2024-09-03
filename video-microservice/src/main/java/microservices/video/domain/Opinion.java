package microservices.video.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Opinion {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional = false)
	private User opinionUser;

	@ManyToOne(optional = false)
	private Video videoOpinion;
	
	@Column(nullable = false)
	private Boolean likeStatus;

	// ID
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	// USER
	public User getUser() {
		return opinionUser;
	}

	public void setUser(User opinionUser) {
		this.opinionUser = opinionUser;
	}
	
	// VIDEO
	public Video getVideo() {
		return videoOpinion;
	}

	public void setVideo(Video videoOpinion) {
		this.videoOpinion = videoOpinion;
	}
	
	// LIKE/DISLIKE
	public Boolean getStatus() {
		return likeStatus;
	}

	public void setStatus(Boolean likeStatus) {
		this.likeStatus = likeStatus;
	}
}
