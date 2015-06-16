package org.rafiq.personality;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;

import org.hibernate.validator.constraints.NotEmpty;
import org.rafiq.personality.translator.impl.MicrosoftAzureTranslator;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;

public class TranslatorFactory {
	@NotEmpty
	private String clientID;

	@NotEmpty
	private String clientSecret;
	
	@NotNull
	private JerseyClientConfiguration httpClientConfig = new JerseyClientConfiguration();

	@JsonProperty
	public String getClientID() {
		return clientID;
	}

	@JsonProperty
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	@JsonProperty
	public String getClientSecret() {
		return clientSecret;
	}

	@JsonProperty
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
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
	 * Creates a {@link Translator} instance based on the configuration
	 * @param env the context of the Translator
	 * @return a {@link Translator} instance
	 */
	public Translator build(Environment env) {
		Client jerseyClient = new JerseyClientBuilder(env).using(httpClientConfig).build("Translator Client");
		MicrosoftAzureTranslator translator = new MicrosoftAzureTranslator(jerseyClient, clientID, clientSecret);
		env.lifecycle().manage(translator);
		return translator;
	}
}
