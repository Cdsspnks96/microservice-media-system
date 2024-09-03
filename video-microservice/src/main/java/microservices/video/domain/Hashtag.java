package microservices.video.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Hashtag {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String tagName;

	@JsonIgnore
	@ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
	private Set<Video> taggedVideos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
	public Set<Video> getTaggedVideos() {
		return taggedVideos;
	}

	public void setTaggedVideos(Set<Video> taggedVideos) {
		this.taggedVideos = taggedVideos;
	}
	
}
