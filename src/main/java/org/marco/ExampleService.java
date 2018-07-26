package org.marco;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ExampleService {

	private ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);

	
	public Response serviceA() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanFail();
		return new Response("Response From Service A");
	}

	
	public Response serviceB() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanDelay();
		return new Response("Response From Service B");
	}

	public Response serviceC() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanFail();
		return new Response("Response From Service C");
	}

	public Response serviceD() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanDelay();
		return new Response("Response From Service D");
	}
	private boolean isFail() {
		return randomGenerator.nextInt(2) != 0;
	}

	private void processThatCanFail() {
		if (isFail())
			throw new IllegalStateException();
	}

	private void processThatCanDelay() {
		if (isFail())
			try {
				Thread.sleep(5000);
			} catch (InterruptedException exc) {
				LOGGER.error(exc.getMessage());
			}
	}
}
