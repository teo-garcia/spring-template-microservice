package com.teogarcia.springmonolith.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;

@Configuration
@ConditionalOnProperty(name = "app.nats-enabled", havingValue = "true", matchIfMissing = true)
public class NatsConfig {

  private static final Logger log = LoggerFactory.getLogger(NatsConfig.class);

  @Value("${app.nats-url:nats://localhost:4222}")
  private String natsUrl;

  @Bean(destroyMethod = "close")
  public Connection natsConnection() throws Exception {
    Options options = new Options.Builder().server(natsUrl).maxReconnects(-1).build();
    log.info("Connecting to NATS at {}", natsUrl);
    return Nats.connect(options);
  }
}
