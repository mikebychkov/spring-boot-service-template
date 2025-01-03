package service.template.aop;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Configuration
public class MyRateLimiterConfiguration {

    private final ConcurrentMap<String, RateLimiter> rateLimiterCache = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final int limitCount = 10;
    private final int limitPeriod = 1;
    private final int limitTimeout = 0;

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.of(buildRegistryConfig());
    }

    public RateLimiter getRateLimiter(String clientId) {
        // TRY TO ACQUIRE EXISTED
        lock.readLock().lock();
        try {
            if (rateLimiterCache.containsKey(clientId)) {
                return rateLimiterCache.get(clientId);
            }
        } finally {
            lock.readLock().unlock();
        }
        // CREATE NEW
        lock.writeLock().lock();
        try {
            return rateLimiterCache.computeIfAbsent(clientId, id -> RateLimiter.of(id, buildRegistryConfig()));
        } finally {
            lock.writeLock().unlock();
        }
    }

    private RateLimiterConfig buildRegistryConfig() {

        return RateLimiterConfig.custom()
                .limitForPeriod(limitCount)
                .limitRefreshPeriod(Duration.ofSeconds(limitPeriod))
                .timeoutDuration(Duration.ofSeconds(limitTimeout))
                .build();
    }
}
