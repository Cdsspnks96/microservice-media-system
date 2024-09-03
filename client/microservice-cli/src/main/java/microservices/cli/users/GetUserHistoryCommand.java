package microservices.cli.users;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.Video;

@Command(name="get-user-history", description="Gets the viewed videos of a specific user", mixinStandardHelpOptions = true)
public class GetUserHistoryCommand implements Runnable {

	@Inject
	private UserClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<Video> videos = client.getViewed(id);
		for (Video video : videos) {
			System.out.println(video);
		}
	}
}