package microservices.subscription.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.subscription.domain.SubUser;

@Repository
public interface SubUserRepository extends CrudRepository<SubUser, Long> {

	@Join(value = "subscriptions", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<SubUser> findById(@NotNull Long id);
	
	Optional<SubUser> findByUsername(String username);
}