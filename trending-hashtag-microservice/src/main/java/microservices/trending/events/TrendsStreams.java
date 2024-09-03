package microservices.trending.events;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import microservices.trending.domain.TrendVideo;

@Factory
public class TrendsStreams {

	public static final String TOPIC_LIKED_BY_DAY = "tags-liked-by-hour";

	@Inject
	private SerdeRegistry serdeRegistry;

	@Singleton
	public KStream<WindowedIdentifier, Long> likeByDay(ConfiguredStreamBuilder builder) {
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "trends-metrics");

		/*
		 * This makes the stream more predictable, and also has the side effect of reducing
		 * Kafka Streams' commit interval to 100ms (rather than the default of 30s, which
		 *  would significantly slow down our tests).
		 */
		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

		KStream<Long, TrendVideo> trendsStream = builder
			.stream(TrendsProducer.TOPIC_LIKED, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(TrendVideo.class)));

		KStream<WindowedIdentifier, Long> stream = trendsStream.groupByKey()
			.windowedBy(TimeWindows.of(Duration.ofHours(1)).advanceBy(Duration.ofHours(1)))
			.count(Materialized.as("liked-by-hour"))
			.toStream()
			.selectKey((k, v) -> new WindowedIdentifier(k.key(), k.window().start(), k.window().end()));

		stream.to(TOPIC_LIKED_BY_DAY,
			Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

		return stream;
	}
}