package microservices.trending.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import microservices.trending.domain.TrendVideo;

@Repository
public interface TrendVideoRepository extends CrudRepository<TrendVideo, Long>{

	@Join(value = "hashtags", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<TrendVideo> findById(@NotNull Long id);

	Optional<TrendVideo> findByVmid(Long vmid);
}