package microservices.video.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.video.domain.Hashtag;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

	Optional<Hashtag> findByTagName(String tagname);
}