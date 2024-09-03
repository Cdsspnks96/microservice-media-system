package microservices.cli.subusers;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-user-subs", description="Subscribes User to hashtag(s)", mixinStandardHelpOptions = true)
public class AddUserSubscriptionsCommand implements Runnable {

	@Inject
	private SubUserClient client;

	@Parameters(index="0")
	private Long userId;

	@Parameters(index="1")
	private String hashtagCSV;

	@Override
	public void run() {
		HttpResponse<String> response = client.addSubscriptions(userId, hashtagCSV);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}
}