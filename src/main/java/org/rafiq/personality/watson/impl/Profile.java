package org.rafiq.personality.watson.impl;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link Profile} class denotes the personality profile of a party
 */
@JsonSnakeCase
public class Profile {
	private TraitTreeNode tree;

	private String id;

	private String source;

	private int wordCount;
	
	private String wordCountMessage;

	@JsonProperty
	public TraitTreeNode getTree() {
		return tree;
	}

	@JsonProperty
	public void setTree(TraitTreeNode tree) {
		this.tree = tree;
	}

	@JsonProperty
	public String getId() {
		return id;
	}

	@JsonProperty
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty
	public String getSource() {
		return source;
	}

	@JsonProperty
	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty
	public int getWordCount() {
		return wordCount;
	}

	@JsonProperty
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	@JsonProperty
	public String getWordCountMessage() {
		return wordCountMessage;
	}

	@JsonProperty
	public void setWordCountMessage(String wordCountMessage) {
		this.wordCountMessage = wordCountMessage;
	}
}
