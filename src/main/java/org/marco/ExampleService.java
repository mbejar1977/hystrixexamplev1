package org.marco;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class ExampleService {

	private ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);

	@HystrixCommand(threadPoolKey = "PoolForServiceAandB")
	public Response serviceA() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanFail();
		return new Response("Response From Service A");
	}

	@HystrixCommand(threadPoolKey = "PoolForServiceAandB", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "6") }, commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") })
	public Response serviceB() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanDelay();
		return new Response("Response From Service B");
	}

	@HystrixCommand(threadPoolKey = "PoolForServiceCandD", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "6") }, commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") })
	public Response serviceC() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanFail();
		return new Response("Response From Service C");
	}

	@HystrixCommand(threadPoolKey = "PoolForServiceCandD", fallbackMethod = "serviceDBis", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") })
	public Response serviceD() {
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
		processThatCanDelay();
		return new Response("Response From Service D");
	}

	private Response serviceDBis() {
		Response response = new Response("Response From service D bis");
		return response;
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
