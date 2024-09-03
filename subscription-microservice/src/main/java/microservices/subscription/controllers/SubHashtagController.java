package microservices.subscription.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

import microservices.subscription.domain.SubHashtag;
import microservices.subscription.repositories.SubHashtagRepository;

@Controller("/subhashtags")
public class SubHashtagController {

	@Inject
	SubHashtagRepository tagRepo;
	
	// HASHTAG LOGIC
	
	@Get("/")
	public Iterable<SubHashtag> list() {
		return tagRepo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		SubHashtag tag = new SubHashtag();
		tag.setTagName(tagName);
		tagRepo.save(tag);
		
		return HttpResponse.created(URI.create("/subhashtags/" + tag.getId()));
	}

	@Get("/{id}")
	public SubHashtag getTag(Long id) {
		return tagRepo.findById(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateTag(Long id, @Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		
		Optional<SubHashtag> oTag = tagRepo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}
		SubHashtag tag = oTag.get();
		
		tag.setTagName(tagName);
		tagRepo.save(tag);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteTag(Long id) {
		Optional<SubHashtag> oTag = tagRepo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}

		tagRepo.delete(oTag.get());
		return HttpResponse.ok();
	}
}
