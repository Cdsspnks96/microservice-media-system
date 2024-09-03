package microservices.video.controllers;

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

import microservices.video.domain.Hashtag;
import microservices.video.events.HashtagProducer;
import microservices.video.repositories.HashtagRepository;

@Controller("/hashtags")
public class HashtagController {

	@Inject
	HashtagRepository repo;
	
	@Inject
	HashtagProducer producer;

	@Get("/")
	public Iterable<Hashtag> list() {
		return repo.findAll();
	}

	@Post("/")
	public HttpResponse<String> add(@Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		
		if (repo.findByTagName(tagName).isPresent()) {
			return HttpResponse.badRequest(String.format("Hashtag %s already exists",tagName));
		}
		Hashtag tag = new Hashtag();
		tag.setTagName(tagName);
		
		tag = repo.save(tag);
		if(tag != null) {
			producer.createTag(tag.getId(), tag);
		}
		
		return HttpResponse.created(URI.create("/hashtags/" + tag.getId()));
	}

	@Get("/{id}")
	public Hashtag getTag(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateTag(Long id, @Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		
		Optional<Hashtag> oTag = repo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}
		Hashtag tag = oTag.get();
		
		tag.setTagName(tagName);
		repo.save(tag);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteTag(Long id) {
		Optional<Hashtag> oTag = repo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}

		repo.delete(oTag.get());
		return HttpResponse.ok();
	}
}
