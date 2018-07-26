package org.marco;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

  private HystrixConcurrencyStrategy currentstrategy;

  public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy currentstrategy) {
    super();
    this.currentstrategy = currentstrategy;
  }

  @Override
  public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
    // TODO Auto-generated method stub
    return currentstrategy != null ? currentstrategy.getBlockingQueue(maxQueueSize)
        : super.getBlockingQueue(maxQueueSize);
  }

  @Override
  public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
    // TODO Auto-generated method stub
    return currentstrategy != null ? currentstrategy.getRequestVariable(rv)
        : super.getRequestVariable(rv);
  }

  @Override
  public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
      HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,
      HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    // TODO Auto-generated method stub
    return currentstrategy != null
        ? currentstrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime,
            unit, workQueue)
        : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit,
            workQueue);
  }

  @Override
  public <T> Callable<T> wrapCallable(Callable<T> callable) {
    // TODO Auto-generated method stub
    return currentstrategy != null
        ? currentstrategy
            .wrapCallable(new DelegatingUserContextCallable<>(callable, DataContainer.getData()))
        : super.wrapCallable(
            new DelegatingUserContextCallable<>(callable, DataContainer.getData()));
  }

}
