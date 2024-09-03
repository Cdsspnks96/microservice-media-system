package microservices.cli.hashtags;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-hashtag", description="Creates a hashtag", mixinStandardHelpOptions = true)
public class AddHashtagCommand implements Runnable {

	@Inject
	private HashtagClient client;

	@Parameters(index="0")
	private String tagName;

	@Override
	public void run() {

		HttpResponse<Void> response = client.add(tagName);
		System.out.println("Server responded with: " + response.getStatus());
	}
}
