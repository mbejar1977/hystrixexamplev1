package org.marco;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<V> implements Callable<V> {

  private final Callable<V> delegate;
  private SessionInfo sessionInfo;

  public V call() throws Exception {
    DataContainer.setData(sessionInfo);
    try {
      return delegate.call();
    } finally {
      this.sessionInfo = null;
    }
  }

  public DelegatingUserContextCallable(Callable<V> delegate, SessionInfo userContext) {
    this.delegate = delegate;
    this.sessionInfo = userContext;
  }

  public static <V> Callable<V> create(Callable<V> delegate, SessionInfo sessionConfig) {
    return new DelegatingUserContextCallable<V>(delegate, sessionConfig);
  }

}
