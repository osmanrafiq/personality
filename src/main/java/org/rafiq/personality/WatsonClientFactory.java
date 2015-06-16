package org.rafiq.personality;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;

import org.hibernate.validator.constraints.NotEmpty;
import org.rafiq.personality.watson.impl.WatsonClientImpl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatsonClientFactory {
	@NotEmpty
	private String serviceURL;

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@NotNull
	private JerseyClientConfiguration httpClientConfig = new JerseyClientConfiguration();

	@JsonProperty
	public String getServiceURL() {
		return serviceURL;
	}

	@JsonProperty
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	@JsonProperty
	public String getUsername() {
		return username;
	}

	@JsonProperty
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
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
	 * Creates a {@link WatsonClient} based on the loaded configuration
	 * @param env the context of the Watson client
	 * @return a {@link WatsonClient} instance
	 */
	public WatsonClient build(Environment env) {
		Client jerseyClient = new JerseyClientBuilder(env).using(httpClientConfig).build("Watson Client");
		WatsonClient client = new WatsonClientImpl(jerseyClient, serviceURL, username, password);
		env.lifecycle().manage(client);
		return client;
	}	
}
