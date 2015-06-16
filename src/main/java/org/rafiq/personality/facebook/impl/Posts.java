package org.rafiq.personality.facebook.impl;

import java.util.List;

import org.rafiq.personality.domain.Post;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link Posts} class represents a collection of Facebook posts for a party
 */
public class Posts {
	private List<Post> data;
	
	private Paging paging;
	
	public Posts() {
		// No argument constructor to support Jackson serialization
	}

	@JsonProperty
	public List<Post> getData() {
		return data;
	}

	@JsonProperty
	public void setData(List<Post> data) {
		this.data = data;
	}

	@JsonProperty
	public Paging getPaging() {
		return paging;
	}

	@JsonProperty
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
}