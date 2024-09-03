package microservices.cli.subusers;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.domain.SubHashtag;

@Command(name="get-user-subs", description="Gets the subscriptions of a specific user", mixinStandardHelpOptions = true)
public class GetUserSubscriptionsCommand implements Runnable {

	@Inject
	private SubUserClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<SubHashtag> tags = client.getSubscriptions(id);
		for (SubHashtag tag : tags) {
			System.out.println(tag);
		}
	}
}