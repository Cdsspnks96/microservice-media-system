package microservices.cli.videos;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-video-tag", description="Adds a hashtag to a video", mixinStandardHelpOptions = true)
public class AddVideoHashtagCommand implements Runnable {

	@Inject
	private VideoClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long tagId;

	@Override
	public void run() {
		HttpResponse<String> response = client.addHashtag(videoId, tagId);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}
}