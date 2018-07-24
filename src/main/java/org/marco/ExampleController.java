package org.marco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class ExampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private ExampleService service;

	@RequestMapping(method = RequestMethod.GET, path = "/case1")
	public Response requestA(@RequestHeader(name = "uuid", required = true) String uuid) {
		preProcess(uuid);
		return service.serviceA();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/case2")
	public Response requestB(@RequestHeader(name = "uuid", required = true) String uuid) {
		preProcess(uuid);
		return service.serviceB();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/case3")
	public Response requestC(@RequestHeader(name = "uuid", required = true) String uuid) {
		preProcess(uuid);
		return service.serviceA();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/case4")
	public Response requestD(@RequestHeader(name = "uuid", required = true) String uuid) {
		preProcess(uuid);
		return service.serviceB();
	}

	private void preProcess(String uuid) {
		DataContainer.getData().setUuid(uuid);
		LOGGER.warn("Header uuid: {}", DataContainer.getData().getUuid());
	}
}
