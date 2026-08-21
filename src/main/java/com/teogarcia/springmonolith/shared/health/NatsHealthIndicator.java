package com.teogarcia.springmonolith.shared.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import io.nats.client.Connection;

@Component("nats")
@ConditionalOnBean(Connection.class)
public class NatsHealthIndicator implements HealthIndicator {

  private final Connection connection;

  public NatsHealthIndicator(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Health health() {
    if (connection.getStatus() == Connection.Status.CONNECTED) {
      return Health.up().withDetail("server", connection.getConnectedUrl()).build();
    }
    return Health.down().withDetail("status", connection.getStatus().name()).build();
  }
}
