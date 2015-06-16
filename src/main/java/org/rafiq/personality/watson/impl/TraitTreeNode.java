package org.rafiq.personality.watson.impl;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link TraitTreeNode} denotes the personality traits of a profile
 */
@JsonSnakeCase
public class TraitTreeNode {
	private String id;

	private String name;
	
	private String category;

	private double percentage;

	private List<TraitTreeNode> children;

	private double samplingError;

	@JsonProperty
	public String getId() {
		return id;
	}

	@JsonProperty
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty()
	public String getCategory() {
		return category;
	}

	@JsonProperty
	public void setCategory(String category) {
		this.category = category;
	}

	@JsonProperty
	public double getPercentage() {
		return percentage;
	}

	@JsonProperty
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@JsonProperty
	public List<TraitTreeNode> getChildren() {
		return children;
	}

	@JsonProperty
	public void setChildren(List<TraitTreeNode> children) {
		this.children = children;
	}

	@JsonProperty
	public double getSamplingError() {
		return samplingError;
	}

	@JsonProperty
	public void setSamplingError(double samplingError) {
		this.samplingError = samplingError;
	}
}
