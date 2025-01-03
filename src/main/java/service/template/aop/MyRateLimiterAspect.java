package service.template.aop;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import service.template.aop.annotation.MyRateLimiter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
@Log4j2
public class MyRateLimiterAspect {

    private final HttpServletRequest httpServletRequest;
    private final MyRateLimiterConfiguration myRateLimiterConfiguration;

    @Pointcut("@annotation(MyRateLimiter)")
    protected void myPointcut() {}

    @Around("myPointcut()")
    public Object rateLimiterProxy(ProceedingJoinPoint joinPoint) throws Throwable {

        UUID traceId = UUID.randomUUID();

        log.debug("Rate Limiter Proxy Open {} {}",
                LocalDateTime.now(),
                traceId);

        validateRateLimiter(joinPoint);

        var rsl = joinPoint.proceed();

        log.debug("Rate Limiter Proxy Closed {} {}",
                LocalDateTime.now(),
                traceId);

        return rsl;
    }

    private void validateRateLimiter(ProceedingJoinPoint joinPoint) {

        var key = getAnnotationParamValue(joinPoint);
        var methodName = joinPoint.getSignature().getName();

        String clientIp = httpServletRequest.getHeader("X-Forwarded-For");
        String rateLimiterName = String.format("%s%s%s", clientIp, methodName, key);
        RateLimiter rateLimiter = myRateLimiterConfiguration.getRateLimiter(rateLimiterName);
        if (!acquirePermission(rateLimiter)) {
            throw RequestNotPermitted.createRequestNotPermitted(rateLimiter);
        }
    }

    private boolean acquirePermission(RateLimiter rateLimiter) {
        try {
            rateLimiter.acquirePermission();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getAnnotationParamValue(ProceedingJoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        MyRateLimiter ann = methodSignature.getMethod().getAnnotation(MyRateLimiter.class);

        return ann.key();
    }
}
