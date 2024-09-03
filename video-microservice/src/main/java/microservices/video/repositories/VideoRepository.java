package microservices.video.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.video.domain.Video;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long>{

	@Join(value = "hashtags", type = Join.Type.LEFT_FETCH)
	@Join(value = "viewers", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<Video> findById(@NotNull Long id);

	//Optional<VideoDTO> findOne(Long id);
}