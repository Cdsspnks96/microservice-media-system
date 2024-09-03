package microservices.cli.opinions;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import microservices.cli.dto.OpinionDTO;

@Command(name="add-video-opinion", description="Like/dislike a video as specified user", mixinStandardHelpOptions = true)
public class AddOpinionCommand implements Runnable {

	@Inject
	private OpinionClient client;
	
	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long userId;
	
	@Parameters(index="2")
	private Integer likeStatus;

	@Override
	public void run() {
		OpinionDTO dto = new OpinionDTO();
		dto.setUserId(userId);
		dto.setVideoId(videoId);
		dto.setStatus(likeStatus);

		HttpResponse<Void> response = client.add(dto);
		System.out.println("Server responded with: " + response.getStatus());
	}
}
