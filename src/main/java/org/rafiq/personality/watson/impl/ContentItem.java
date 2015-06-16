package org.rafiq.personality.watson.impl;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@link ContentItem} denotes a single post for delivery to Watson for analysis
 */
public class ContentItem {
	/**
	 * The unique id of the post
	 */
	@NotEmpty
	private String id;

	/**
	 * The creator of the post
	 */
	@NotEmpty
	private String userid;
	
	/**
	 * The source of the post i.e. Facebook
	 */
	@NotEmpty
	private String sourceid = "facebook";

	/**
	 * Milliseconds since 1970 when the post was created
	 */
	private int created;

	/**
	 * MIME type of content
	 */
	@NotEmpty
	private String contenttype = "text/plain";
	
	/**
	 * Language the post is in
	 */
	private String language = "en";
	
	/**
	 * The content
	 */
	@NotEmpty
	private String content;
	
	/**
	 * Unique id of the parent if any
	 */
	private String parentid;
	
	/**
	 * Indicates if this post is a reply
	 */
	private boolean reply = false;
	
	/**
	 * Indicates if this post is a forwarded copy
	 */
	private boolean forward = false;

	public ContentItem() {
		//No argument constructor for serialization
	}
	
	public ContentItem(String id, String userid, int created, String content) {
		this.id = id;
		this.userid = userid;
		this.created = created;
		this.content = content;
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
	public String getUserid() {
		return userid;
	}

	@JsonProperty
	public void setUserid(String userid) {
		this.userid = userid;
	}

	@JsonProperty
	public String getSourceid() {
		return sourceid;
	}

	@JsonProperty
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	@JsonProperty
	public int getCreated() {
		return created;
	}

	@JsonProperty
	public void setCreated(int created) {
		this.created = created;
	}

	@JsonProperty
	public String getContenttype() {
		return contenttype;
	}
	
	@JsonProperty
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	@JsonProperty
	public String getLanguage() {
		return language;
	}

	@JsonProperty
	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	@JsonProperty
	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getParentid() {
		return parentid;
	}

	@JsonProperty
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@JsonProperty
	public boolean isReply() {
		return reply;
	}

	@JsonProperty
	public void setReply(boolean reply) {
		this.reply = reply;
	}

	@JsonProperty
	public boolean isForward() {
		return forward;
	}

	@JsonProperty
	public void setForward(boolean forward) {
		this.forward = forward;
	}
}