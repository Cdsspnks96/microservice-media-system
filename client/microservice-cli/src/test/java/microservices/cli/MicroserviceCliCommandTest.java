package microservices.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MicroserviceCliCommandTest {

    @Test
    public void testWithCommandLineOption() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[] { "-v" };
            PicocliRunner.run(MicroserviceCliCommand.class, ctx, args);

            // microservice-cli
            assertTrue(baos.toString().contains("Hi!"));
        }
    }
    
    @Test
    public void addThenGetBook() throws Exception {
        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            PicocliRunner.run(MicroserviceCliCommand.class, ctx, new String[] { "add-user", "Jeff" });
            assertTrue(baos.toString().contains("CREATED"), "The new user was created");
            
            baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            PicocliRunner.run(MicroserviceCliCommand.class, ctx, new String[] { "add-video", "Container Security", "1" });
            assertTrue(baos.toString().contains("CREATED"), "The new video was created");

            baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            PicocliRunner.run(MicroserviceCliCommand.class, ctx, new String[] { "get-videos" });
            final String newOutput = baos.toString().trim();
			final String[] newLines = newOutput.split(System.lineSeparator());

			int lineNumber = 0;
			assertTrue(newLines.length > 0, "There is at least one line of output");
			for (String line : newLines) {
				++lineNumber;
				assertTrue(line.startsWith("Video"), String.format("Line %d starts with 'Video': %s", lineNumber, line));
			}
        }
    }
}