package microservices.cli.subusers;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="delete-user-sub", description="Unsubscribes user from a hashtag", mixinStandardHelpOptions = true)
public class DeleteUserSubscriptionCommand implements Runnable {

	@Parameters(index="0")
	private Long userId;

	@Parameters(index="1")
	private Long tagId;

	@Inject
	private SubUserClient client;

	@Override
	public void run() {
		HttpResponse<String> response = client.removeSubscription(userId, tagId);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}
}