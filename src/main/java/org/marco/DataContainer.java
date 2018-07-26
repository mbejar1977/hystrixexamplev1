package org.marco;

import java.util.Objects;
import org.apache.commons.configuration.event.ConfigurationErrorEvent;

public class DataContainer {

  private static ThreadLocal<SessionInfo> container = new ThreadLocal<>();

  public static SessionInfo getData() {
    SessionInfo data = container.get();
    if (Objects.isNull(data)) {
      data = new SessionInfo();
      container.set(data);
    }
    return data;
  }

  public static void setData(SessionInfo data) {
    Objects.requireNonNull(data);
    container.set(data);
  }
}
