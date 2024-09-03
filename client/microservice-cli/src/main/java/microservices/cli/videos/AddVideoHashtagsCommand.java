package microservices.cli.videos;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-video-tags", description="Adds hashtag(s) to a video", mixinStandardHelpOptions = true)
public class AddVideoHashtagsCommand implements Runnable {

	@Inject
	private VideoClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private String hashtagCSV;

	@Override
	public void run() {
		HttpResponse<String> response = client.addHashtags(videoId, hashtagCSV);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}
}