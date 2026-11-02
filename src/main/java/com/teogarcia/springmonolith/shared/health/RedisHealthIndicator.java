package com.teogarcia.springmonolith.shared.health;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component("redis")
public class RedisHealthIndicator implements HealthIndicator {

  private final RedisConnectionFactory factory;

  public RedisHealthIndicator(RedisConnectionFactory factory) {
    this.factory = factory;
  }

  @Override
  public Health health() {
    try {
      factory.getConnection().ping();
      return Health.up().build();
    } catch (Exception ex) {
      return Health.down(ex).build();
    }
  }
}
