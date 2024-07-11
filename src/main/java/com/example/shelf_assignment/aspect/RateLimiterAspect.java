package com.example.shelf_assignment.aspect;

import com.example.shelf_assignment.annotation.RateLimit;
import com.example.shelf_assignment.dto.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimiterAspect {

    private final Map<String, RequestCount> requests = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object limit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long currentTime = System.currentTimeMillis() / 1000;

        RequestCount requestCount = requests.compute(methodName, (key, val) -> {
            if (val == null || val.timestamp + rateLimit.duration() <= currentTime) {
                return new RequestCount(currentTime, 1);
            }
            val.count++;
            return val;
        });

        if (requestCount.count <= rateLimit.limit()) {
            return joinPoint.proceed();
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO(false, "Rate limit exceeded. Try again later.", null));
        }
    }

    private static class RequestCount {
        long timestamp;
        int count;

        RequestCount(long timestamp, int count) {
            this.timestamp = timestamp;
            this.count = count;
        }
    }
}