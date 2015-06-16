package org.rafiq.personality.health;

import com.codahale.metrics.health.HealthCheck;

public class FeedHealthCheck extends HealthCheck{
	
	public FeedHealthCheck() {
	}
	
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
