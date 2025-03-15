package ac.knu.likeknu.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger apiLogger = LoggerFactory.getLogger("api.request");
    private final ObjectMapper objectMapper;

    public ApiLoggingFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/api")) {
            filterChain.doFilter(request, response);
            return;
        }

        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            Map<String, Object> logData = new HashMap<>();
            logData.put("timestamp", LocalDateTime.now().toString());
            logData.put("method", request.getMethod());
            logData.put("uri", request.getRequestURI());
            logData.put("queryString", request.getQueryString());
            logData.put("duration", duration + "ms");
            logData.put("status", responseWrapper.getStatus());
            logData.put("userAgent", request.getHeader("User-Agent"));
            logData.put("deviceId", request.getHeader("Device-Id"));

            String requestBody = new String(requestWrapper.getContentAsByteArray());
            if (StringUtils.hasText(requestBody)) {
                logData.put("requestBody", requestBody);
            }

            apiLogger.info(objectMapper.writeValueAsString(logData));
            responseWrapper.copyBodyToResponse();
        }
    }
}
