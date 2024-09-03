package microservices.video.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import microservices.video.domain.Opinion;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion, Long> {

	@Override
	Optional<Opinion> findById(@NotNull Long id);
}