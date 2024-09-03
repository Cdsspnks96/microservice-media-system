package microservices.cli.hashtags;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import microservices.cli.domain.Hashtag;

@Command(name="get-hashtags", description="Gets all the tags", mixinStandardHelpOptions = true)
public class GetHashtagsCommand implements Runnable {

	@Inject
	private HashtagClient client;

	@Override
	public void run() {
		for (Hashtag t : client.list()) {
			System.out.println(t);
		}
	}
}