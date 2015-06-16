package org.rafiq.personality;

import io.dropwizard.lifecycle.Managed;

import java.util.Date;
import java.util.List;

import org.rafiq.personality.domain.Post;

/**
 * The {@link FacebookClient} provides the means to retrieve posts made by a party on Facebook
 */
public interface FacebookClient extends Managed{
	/**
	 * Retrieves the posts made by the specified party
	 * @param party the party to retrieve the posts of
	 * @return a list of posts
	 */
	public List<Post> getPosts(String party);
	
	/**
	 * Retrieves the posts made by the specified party since the specified date
	 * @param party the party to retrieve the posts of
	 * @param since the date after which the posts must be from
	 * @return a list of posts since the specified date
	 */
	public List<Post> getPosts(String party, Date since);
}
