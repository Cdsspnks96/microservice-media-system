package microservices.subscription.events;

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

import microservices.subscription.domain.SubUser;

@Factory
public class SubsStreams {

	public static final String TOPIC_SUBBED_BY_DAY = "tags-subbed-by-day";

	@Inject
	private SerdeRegistry serdeRegistry;

	@Singleton
	public KStream<WindowedIdentifier, Long> subByDay(ConfiguredStreamBuilder builder) {
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "subs-metrics");

		/*
		 * This makes the stream more predictable, and also has the side effect of reducing
		 * Kafka Streams' commit interval to 100ms (rather than the default of 30s, which
		 *  would significantly slow down our tests).
		 */
		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

		KStream<Long, SubUser> subsStream = builder
			.stream(SubsProducer.TOPIC_SUB, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(SubUser.class)));

		KStream<WindowedIdentifier, Long> stream = subsStream.groupByKey()
			.windowedBy(TimeWindows.of(Duration.ofDays(1)).advanceBy(Duration.ofDays(1)))
			.count(Materialized.as("subbed-by-day"))
			.toStream()
			.selectKey((k, v) -> new WindowedIdentifier(k.key(), k.window().start(), k.window().end()));

		stream.to(TOPIC_SUBBED_BY_DAY,
			Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

		return stream;
	}
}