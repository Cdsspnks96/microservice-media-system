package microservices.trending.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import microservices.trending.domain.TrendHashtag;

@Repository
public interface TrendHashtagRepository extends CrudRepository<TrendHashtag, Long> {

	Optional<TrendHashtag> findByTagName(String tagname);
}