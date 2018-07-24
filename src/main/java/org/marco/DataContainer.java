package org.marco;

public class DataContainer {

	private static ThreadLocal<SessionInfo> container = new ThreadLocal<>();

	public static SessionInfo getData() {
		SessionInfo data = container.get();
		if (data == null) {
			data = new SessionInfo();
			container.set(data);
		}
		return data;
	}
}
