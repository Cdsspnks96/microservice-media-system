package microservices.trending.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class OpinionDTO {

	private Long id;
	
	private UserDTO opinionUser;

	private VideoDTO videoOpinion;
	
	private Boolean likeStatus;

	// ID
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	// USER
	public UserDTO getUser() {
		return opinionUser;
	}

	public void setUser(UserDTO opinionUser) {
		this.opinionUser = opinionUser;
	}
	
	// VIDEO
	public VideoDTO getVideo() {
		return videoOpinion;
	}

	public void setVideo(VideoDTO videoOpinion) {
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
