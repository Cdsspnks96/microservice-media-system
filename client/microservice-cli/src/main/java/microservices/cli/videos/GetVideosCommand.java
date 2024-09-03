package microservices.cli.videos;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import microservices.cli.domain.Video;

@Command(name="get-videos", description="Gets all the videos", mixinStandardHelpOptions = true)
public class GetVideosCommand implements Runnable {

	@Inject
	private VideoClient client;

	@Override
	public void run() {
		for (Video b : client.list()) {
			System.out.println(b);
		}
	}

}
