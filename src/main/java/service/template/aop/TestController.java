package service.template.aop;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.template.aop.annotation.MyRateLimiter;

@RestController
@RequestMapping("/aop/test")
public class TestController {

    @MyRateLimiter
    @GetMapping
    public String testEndpoint() {

        return "test";
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<String> exceptionHandler(RequestNotPermitted e) {

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
    }
}
