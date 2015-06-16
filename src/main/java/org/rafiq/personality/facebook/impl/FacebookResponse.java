
package org.rafiq.personality.facebook.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link FacebookResponse} class denotes Facebooks response to a post query
 */
public class FacebookResponse {
	private Posts posts;
	private String id;
	
	@JsonProperty
	public Posts getPosts() {
		return posts;
	}
	
	@JsonProperty
	public void setPosts(Posts posts) {
		this.posts = posts;
	}
	
	@JsonProperty
	public String getId() {
		return id;
	}

	@JsonProperty
	public void setId(String id) {
		this.id = id;
	}
}
