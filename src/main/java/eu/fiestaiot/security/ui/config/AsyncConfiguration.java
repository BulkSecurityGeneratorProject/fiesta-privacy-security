package eu.fiestaiot.security.ui.config;

import io.github.jbooter.async.ExceptionHandlingAsyncTaskExecutor;
import io.github.jbooter.config.JBooterProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    private final JBooterProperties jBooterProperties;

    public AsyncConfiguration(JBooterProperties jBooterProperties) {
        this.jBooterProperties = jBooterProperties;
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(jBooterProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(jBooterProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(jBooterProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("fiesta-security-ui-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
