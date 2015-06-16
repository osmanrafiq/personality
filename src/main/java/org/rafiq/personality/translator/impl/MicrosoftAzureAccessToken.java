package org.rafiq.personality.translator.impl;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The {@link MicrosoftAzureAccessToken} denotes the token retrieved from Microsoft Azure to access the translation API
 * @author Osman
 *
 */
@JsonSnakeCase
public class MicrosoftAzureAccessToken {
	private String accessToken;
	
	private String tokenType;
	
	private int expiresIn;
	
	private String scope;
	
	private long creationTime = new Date().getTime();

	@JsonProperty
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty
	public String getTokenType() {
		return tokenType;
	}

	@JsonProperty
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Get validity period of token in seconds
	 * @return no. of seconds this token is valid for
	 */
	@JsonProperty
	public int getExpiresIn() {
		return expiresIn;
	}

	@JsonProperty
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@JsonProperty
	public String getScope() {
		return scope;
	}

	@JsonProperty
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * Indicates whether this access token has expired
	 * @return true if token has expired and false otherwise
	 */
	public boolean isExpired() {
		return new Date().getTime() > creationTime + expiresIn - 60;
	}
	
	@Override
	public String toString() {
		return "accessToken: " + accessToken + ", expiresIn: " + expiresIn;
	}
}