package microservices.cli.subusers;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.SubVideo;

@Command(name="get-user-top-ten", description="Gets the top 10 unwatched videos for a user from a specific tag", mixinStandardHelpOptions = true)
public class GetUserTopTenCommand implements Runnable {

	@Inject
	private SubUserClient client;

	@Parameters(index="0")
	private Long userId;
	
	@Parameters(index="1")
	private Long tagId;

	@Override
	public void run() {
		Iterable<SubVideo> videos = client.getUserTopTen(userId,tagId);
		for (SubVideo video : videos) {
			System.out.println(video);
		}
	}
}