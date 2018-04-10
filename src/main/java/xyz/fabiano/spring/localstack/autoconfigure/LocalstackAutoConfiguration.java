package xyz.fabiano.spring.localstack.autoconfigure;

import cloud.localstack.docker.LocalstackDocker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@ConditionalOnProperty("spring.localstack.autoconfiguration")
public class LocalstackAutoConfiguration {

    private LocalstackDocker localstackDocker;

    @Bean
    public LocalstackDocker localstackDocker() {
        localstackDocker = LocalstackDocker.getLocalstackDocker();
        localstackDocker.startup();
        return localstackDocker;
    }

    @EventListener({ ContextStoppedEvent.class, ContextClosedEvent.class })
    public void cleanContext() {
        if(localstackDocker != null) {
            localstackDocker.stop();
        }
    }
}