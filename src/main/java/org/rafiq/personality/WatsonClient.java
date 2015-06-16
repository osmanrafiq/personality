package org.rafiq.personality;

import io.dropwizard.lifecycle.Managed;

import java.util.List;

import org.rafiq.personality.domain.Post;
import org.rafiq.personality.watson.impl.Profile;

/**
 * The {@link WatsonClient} provides the means to perform personality insight analysis using the Watson Developer Cloud
 */
public interface WatsonClient extends Managed{

	/**
	 * Performs the personality insight analysis
	 * @param party the name of the party
	 * @param posts the posts made by the party
	 * @return the personality profile
	 */
	Profile analyze(String party, List<Post> posts);
}
