package org.rafiq.personality;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;

import org.hibernate.validator.constraints.NotEmpty;
import org.rafiq.personality.facebook.impl.FacebookClientImpl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacebookClientFactory {
	@NotEmpty
	private String accessToken;

	@NotNull
	private JerseyClientConfiguration httpClientConfig = new JerseyClientConfiguration();

	@JsonProperty
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("httpClient")
	public JerseyClientConfiguration getHttpClientConfig() {
		return httpClientConfig;
	}

	@JsonProperty("httpClient")
	public void setHttpClientConfig(JerseyClientConfiguration httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	/**
	 * Creates a {@link FacebookClient} based on the loaded configuration
	 * @param env the context of the Facebook client
	 * @return a {@link FacebookClient} instance
	 */
	public FacebookClient build(Environment env) {
		Client jerseyClient = new JerseyClientBuilder(env).using(httpClientConfig).build("Facebook Client");
		FacebookClient facebookClient = new FacebookClientImpl(jerseyClient, accessToken);
		env.lifecycle().manage(facebookClient);
		return facebookClient;
	}
}
