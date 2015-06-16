package org.rafiq.personality.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.hibernate.validator.constraints.NotEmpty;
import org.rafiq.personality.facebook.impl.UnixTimestampDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The {@link Post} class represents a single Facebook post
 */
public class Post implements Comparable<Post>{
	@NotEmpty
	private String message;

	@NotEmpty
	private String id;

	@NotEmpty
	private Date created;
	
	public Post()  {
		// No argument constructor to support Jackson serialization
	}

	/**
	 * Creates a {@link Post} instance
	 * @param id the unique ID of the post
	 * @param created the date when the post was created
	 * @param message the content of the post
	 */
	public Post(String id, Date created, String message) {
		this.id = id;
		this.created = created;
		this.message = message;
	}
	
	@JsonProperty
	public String getMessage() {
		return message;
	}

	@JsonProperty
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty
	public String getId() {
		return id;
	}

	@JsonProperty
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("created_time")
	@JsonDeserialize(using = UnixTimestampDeserializer.class)
	public Date getCreated() {
		return created;
	}

	@JsonProperty("created_time")
	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Post) {
			Post other = (Post) obj;
			return id == other.id || (id != null && id.equals(other.id));
		}

		return false;
	}
	
	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return id +", " + formatter.format(created)  + ", " + message;
	}
	
	@Override
	public int compareTo(Post o) {
		return (int) (created.getTime() - o.created.getTime());
	}
}