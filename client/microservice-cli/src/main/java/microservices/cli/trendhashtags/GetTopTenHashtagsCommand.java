package microservices.cli.trendhashtags;

import jakarta.inject.Inject;
import microservices.cli.domain.TrendHashtag;
import picocli.CommandLine.Command;

@Command(name="get-top-tags", description="Gets the top 10 trending hashtags", mixinStandardHelpOptions = true)
public class GetTopTenHashtagsCommand implements Runnable {

	@Inject
	private TrendHashtagClient client;

	@Override
	public void run() {
		Iterable<TrendHashtag> tags = client.getTopTenTags();
		for (TrendHashtag tag : tags) {
			System.out.println(tag);
		}
	}
}