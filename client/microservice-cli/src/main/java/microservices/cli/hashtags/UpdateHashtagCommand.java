package microservices.cli.hashtags;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name="update-hashtag", description="Updates a hashtag", mixinStandardHelpOptions = true)
public class UpdateHashtagCommand implements Runnable {

	@Parameters(index="0")
	private Long id;

	@Option(names = {"-t", "--tagname"}, description="Name of the hashtag")
	private String tagname;

	@Inject
	private HashtagClient client;

	@Override
	public void run() {		
		HttpResponse<Void> response = client.updateTag(id, tagname);
		System.out.println("Server responded with: " + response.getStatus());
	}
}

