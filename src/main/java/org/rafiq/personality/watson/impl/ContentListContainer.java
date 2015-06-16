package org.rafiq.personality.watson.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link ContentListContainer} denotes a container for the posts sent to Watson for personality analysis
 */
public class ContentListContainer {
	private List<ContentItem> contentItems;

	@JsonProperty
	public List<ContentItem> getContentItems() {
		return contentItems;
	}

	@JsonProperty
	public void setContentItems(List<ContentItem> contentItems) {
		this.contentItems = contentItems;
	}
}
