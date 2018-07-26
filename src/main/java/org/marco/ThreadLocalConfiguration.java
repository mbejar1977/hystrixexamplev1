package org.marco;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.netflix.hystrix.HystrixMetrics;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

@Configuration
public class ThreadLocalConfiguration {
  @Autowired(required = false)
  private HystrixConcurrencyStrategy existingConcurrencyStrategy;

  @PostConstruct
  public void init() {
    HystrixEventNotifier notifier = HystrixPlugins.getInstance().getEventNotifier();
    HystrixMetricsPublisher metrics = HystrixPlugins.getInstance().getMetricsPublisher();
    HystrixPropertiesStrategy strategy = HystrixPlugins.getInstance().getPropertiesStrategy();
    HystrixCommandExecutionHook executionHook =
        HystrixPlugins.getInstance().getCommandExecutionHook();
    HystrixPlugins.getInstance()
        .registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
//    HystrixPlugins.getInstance().registerEventNotifier(notifier);
//    HystrixPlugins.getInstance().registerMetricsPublisher(metrics);
//    HystrixPlugins.getInstance().registerPropertiesStrategy(strategy);
//    HystrixPlugins.getInstance().registerCommandExecutionHook(executionHook);
  }
}
