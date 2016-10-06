package seekers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;

@EnableBinding(Sink.class)
public class PriceSink {

	private static final Logger LOGGER = LoggerFactory.getLogger(PriceSink.class);

  	private PriceRepository repository;

	@ServiceActivator(inputChannel=Sink.INPUT)
	public void persistPrice(Object payload) {
		if(payload == null) {
			throw new IllegalArgumentException("Payload is null");
		}

		if(!(payload instanceof String)) {
			throw new IllegalArgumentException("Payload have to a string");
		}

		String priceWithTimestamp = (String) payload;

		if(!priceWithTimestamp.contains("@")) {
			throw new IllegalArgumentException("Payload format is not valid");
		}

		String[] parts = priceWithTimestamp.split("@");

		if(parts.length == 2) {
			repository.save(new Price(parts[0], parts[1]));
		}
	}

	@Autowired
	public void setRepository(PriceRepository repository) {
		this.repository = repository;
	}
}
