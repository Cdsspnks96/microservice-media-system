package microservices.subscription.dto;

import java.util.HashSet;
import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserDTO {

	private Long id;

	private String username;
	
	private Set<VideoDTO> postedVideos;
	
	private Set<VideoDTO> viewedVideos;
	
	private Set<OpinionDTO> opinions;
	
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
	public Set<VideoDTO> getPostedVideos() {
		return postedVideos;
	}

	public void setPostedVideos(Set<VideoDTO> postedVideos) {
		this.postedVideos = postedVideos;
	}
	
	// User history
	public Set<VideoDTO> getViewedVideos() {
		return viewedVideos;
	}

	public void setViewedVideos(Set<VideoDTO> viewedVideos) {
		this.viewedVideos = viewedVideos;
	}
	
	// User opinions
	public Set<OpinionDTO> getOpinions() {
		return opinions;
	}

	public void setOpinions(Set<OpinionDTO> opinions) {
		this.opinions = opinions;
	}
	
	public Set<VideoDTO> getLikedVideos() {
		Set<VideoDTO> likedVideos = new HashSet<VideoDTO>();
		for(OpinionDTO opinion : opinions) {
			if(opinion.getStatus()) {
				likedVideos.add(opinion.getVideo());
			}
		}
		return likedVideos;
	}
	
	public Set<VideoDTO> getDislikedVideos() {
		Set<VideoDTO> dislikedVideos = new HashSet<VideoDTO>();
		for(OpinionDTO opinion : opinions) {
			if(!opinion.getStatus()) {
				dislikedVideos.add(opinion.getVideo());
			}
		}
		return dislikedVideos;
	}
}
