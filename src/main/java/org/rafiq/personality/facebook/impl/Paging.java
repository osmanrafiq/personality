package org.rafiq.personality.facebook.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link Paging} class represents the paging response received from Facebook
 */
public class Paging {
	private String previous;
	private String next;
	
	@JsonProperty
	public String getPrevious() {
		return previous;
	}
	
	@JsonProperty
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	
	@JsonProperty
	public String getNext() {
		return next;
	}
	
	@JsonProperty
	public void setNext(String next) {
		this.next = next;
	}
}
