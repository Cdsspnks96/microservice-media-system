package microservices.cli.videos;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.Video;

@Command(name="get-video", description="Gets a specific video", mixinStandardHelpOptions = true)
public class GetVideoCommand implements Runnable {

	@Inject
	private VideoClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Video video = client.getVideo(id);
		if (video == null) {
			System.err.println("Video not found!");
			System.exit(1);
		} else {
			System.out.println(video);
		}
	}

	
}