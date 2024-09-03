package microservices.cli.users;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.dto.UserDTO;

@Command(name="get-user", description="Gets a specific user", mixinStandardHelpOptions = true)
public class GetUserCommand implements Runnable {

	@Inject
	private UserClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		UserDTO user = client.getUser(id);
		if (user == null) {
			System.err.println("User not found!");
			System.exit(1);
		} else {
			System.out.println(user);
		}
	}	
}