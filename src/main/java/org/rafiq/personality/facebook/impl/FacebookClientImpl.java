package org.rafiq.personality.facebook.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.IOUtils;
import org.rafiq.personality.FacebookClient;
import org.rafiq.personality.domain.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookClientImpl implements FacebookClient{
	private static final Logger logger = LoggerFactory.getLogger(FacebookClientImpl.class);

	private final Client httpClient;
	private final String accessToken;
	
	public FacebookClientImpl(Client httpClient, String accessToken) {
		this.httpClient = httpClient;
		this.accessToken = accessToken;
	}
	
	@Override
	public List<Post> getPosts(String party) {
		return getPosts(party, null);
	}

	@Override
	public List<Post> getPosts(String party, Date since) {
		UriBuilder builder = null;
		if (since != null) {
			builder = UriBuilder.fromUri("https://graph.facebook.com/v2.3/{party}?date_format=U&fields=posts.limit(250).fields(message).since({since})");
			builder.resolveTemplate("since", since.getTime() / 1000);
		} else {
			builder = UriBuilder.fromUri("https://graph.facebook.com/v2.3/{party}?date_format=U&fields=posts.limit(250).fields(message)");
		}
		
		builder.resolveTemplate("party", party);
		return getPosts(builder);
	}

	/**
	 * Retrieves the posts from the specified URI
	 * @param builder the URI builder
	 * @return a list of {@link Post} from the specified URI
	 */
	private List<Post> getPosts(UriBuilder builder) {
		WebTarget target = httpClient.target(builder);
		Builder invocationBuilder = target.request().accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "OAuth " + accessToken);
		Response response = invocationBuilder.get();
		
		List<Post> posts = new ArrayList<Post>();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			FacebookResponse fbResponse = response.readEntity(FacebookResponse.class);
			if (fbResponse.getPosts() != null) {
				posts = fbResponse.getPosts().getData();
			}
		} else {
			InputStream stream = (InputStream) response.getEntity();
			try {
				logger.error(IOUtils.toString(stream, StandardCharsets.UTF_8));
			} catch (IOException e) {
				logger.error("Error retrieval failed", e);
			}
		}
		return posts;
	}
	
	@Override
	public void start() throws Exception {
		//Nothing to do
	}

	@Override
	public void stop() throws Exception {
		httpClient.close();
	}
}
