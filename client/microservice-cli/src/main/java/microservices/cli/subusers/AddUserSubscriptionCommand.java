package microservices.cli.subusers;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-user-sub", description="Subscribes a user to a hashtag", mixinStandardHelpOptions = true)
public class AddUserSubscriptionCommand implements Runnable {

	@Inject
	private SubUserClient client;

	@Parameters(index="0")
	private Long userId;

	@Parameters(index="1")
	private Long tagId;

	@Override
	public void run() {
		HttpResponse<String> response = client.addSubscription(userId, tagId);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}
}