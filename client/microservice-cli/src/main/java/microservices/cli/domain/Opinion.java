package microservices.cli.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Opinion {

	private Long id;
	private User opinionUser;
	private Video videoOpinion;
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

	@Override
	public String toString() {
		return "OpinionDTO ["
				+ "user=" + opinionUser.getUsername()
				+ "video=" + videoOpinion.getTitle() 
				+ "likeStatus=" + likeStatus
				+ "]";
	}
}
