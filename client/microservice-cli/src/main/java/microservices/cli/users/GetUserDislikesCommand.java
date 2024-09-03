package microservices.cli.users;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.Video;

@Command(name="get-user-dislikes", description="Gets the dislikes of a specific user", mixinStandardHelpOptions = true)
public class GetUserDislikesCommand implements Runnable {

	@Inject
	private UserClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<Video> videos = client.getDisliked(id);
		for (Video video : videos) {
			System.out.println(video);
		}
	}
}