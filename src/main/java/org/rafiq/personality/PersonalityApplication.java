package org.rafiq.personality;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.util.List;
import java.util.function.Function;

import org.rafiq.personality.domain.Post;
import org.rafiq.personality.health.FeedHealthCheck;
import org.rafiq.personality.jdbi.PostsDAO;
import org.rafiq.personality.watson.impl.Profile;
import org.skife.jdbi.v2.DBI;

public class PersonalityApplication extends Application<PersonalityConfiguration>{

	public static void main(String[] args) throws Exception{
		PersonalityApplication application = new PersonalityApplication();
		application.run(args);
	}
	
	@Override
	public void run(PersonalityConfiguration config, Environment env)
			throws Exception {
		
		FeedHealthCheck receiverHealth = new FeedHealthCheck();
		env.healthChecks().register("receiver health", receiverHealth);

		// Initialize database
		DBIFactory dbiFactory = new DBIFactory();
		DBI dbi = dbiFactory.build(env, config.getDatabase(), "");
		final PostsDAO postsDao = dbi.onDemand(PostsDAO.class);
		postsDao.createPostsTable();
		
		// get Facebook client
		final FacebookClient facebookClient = config.getFacebook().build(env);
		
		// get Translator client
		final Translator translator = config.getTranslator().build(env);
		
		//get Watson client
		final WatsonClient watson = config.getWatson().build(env);
		
		// Setup processing
		//TODO move this into separate bean
		for (String parti : config.getParties()) {
			// Retrieve and store latest posts
			Post latestPost = postsDao.getLatestPost(parti);
			List<Post> posts = facebookClient.getPosts(parti, latestPost != null ? latestPost.getCreated() : null);
			posts.stream().sequential().filter(p -> p.getMessage() != null).forEach(p -> postsDao.insert(parti, p, translator.determineLanguage(p.getMessage())));
			
			// Translate any untranslated posts
			List<Post> untranslated = postsDao.getUntranslatedPosts(parti, "en");
			untranslated.stream().sequential().map(new Function<Post, Post>() {
				@Override
				public Post apply(Post original) {
					String translation = translator.translate(original.getMessage(), "da", "en");
					Post translated = null;
					if (translation != null) {
						translated = new Post(original.getId(), original.getCreated(), translation);
					}
					return translated;
				}
			}).filter(p -> p != null).forEach(p -> postsDao.insert(parti, p, "en"));
			
			// Request profiles
			List<Post> translated = postsDao.getPosts(parti, "en");
			Profile profile = watson.analyze(parti, translated);
		}
	}

}
