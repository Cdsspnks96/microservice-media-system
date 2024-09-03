package microservices.video.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.video.domain.User;
import microservices.video.dto.UserDTO;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<UserDTO> findOne(Long id);
	
	Optional<User> findByUsername(String username);
}