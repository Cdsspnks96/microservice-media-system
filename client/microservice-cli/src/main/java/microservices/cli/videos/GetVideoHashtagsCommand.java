package microservices.cli.videos;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.Hashtag;

@Command(name="get-video-tags", description="Gets the tags of a specific video", mixinStandardHelpOptions = true)
public class GetVideoHashtagsCommand implements Runnable {

	@Inject
	private VideoClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<Hashtag> tags = client.getHashtags(id);
		for (Hashtag tag : tags) {
			System.out.println(tag);
		}
	}
}