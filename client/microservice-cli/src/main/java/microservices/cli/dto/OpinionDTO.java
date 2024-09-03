package microservices.cli.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class OpinionDTO {

	private Long userId;
	private Long videoId;
	private Integer likeStatus;

	public Boolean isNotFull() {
		return (userId.equals(null) ||
				videoId.equals(null) ||
				likeStatus.equals(null)
				);
	}

	// USER
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	// VIDEO
	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	// LIKE/DISLIKE
	public Integer getStatus() {
		return likeStatus;
	}

	public void setStatus(Integer likeStatus) {
		this.likeStatus = likeStatus;
	}

	@Override
	public String toString() {
		return "OpinionDTO ["
				+ "userID=" + userId
				+ "videoID=" + videoId 
				+ "likeStatus=" + likeStatus
				+ "]";
	}
}