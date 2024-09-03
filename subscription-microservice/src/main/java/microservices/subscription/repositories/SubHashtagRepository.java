package microservices.subscription.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.subscription.domain.SubHashtag;

@Repository
public interface SubHashtagRepository extends CrudRepository<SubHashtag, Long> {

	Optional<SubHashtag> findByTagName(String tagname);
}