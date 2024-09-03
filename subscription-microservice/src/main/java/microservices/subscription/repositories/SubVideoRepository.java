package microservices.subscription.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import microservices.subscription.domain.SubVideo;

@Repository
public interface SubVideoRepository extends CrudRepository<SubVideo, Long>{

	@Join(value = "hashtags", type = Join.Type.LEFT_FETCH)
	@Join(value = "viewers", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<SubVideo> findById(@NotNull Long id);

	Optional<SubVideo> findByVmid(Long vmid);
}