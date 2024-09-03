package microservices.cli.users;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.dto.UserDTO;

@Command(name="add-user", description="Adds a user", mixinStandardHelpOptions = true)
public class AddUserCommand implements Runnable {

	@Inject
	private UserClient client;

	@Parameters(index="0")
	private String username;

	@Override
	public void run() {
		UserDTO dto = new UserDTO();
		dto.setUsername(username);

		HttpResponse<Void> response = client.add(dto);
		System.out.println("Server responded with: " + response.getStatus());
	}
}