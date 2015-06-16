package org.rafiq.personality.watson.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.rafiq.personality.WatsonClient;
import org.rafiq.personality.domain.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WatsonClientImpl implements WatsonClient{
	private static final Logger logger = LoggerFactory.getLogger(WatsonClient.class);

	private final Client httpClient;
	private final String serviceURL;
	private final String authentication;

	public WatsonClientImpl(Client httpClient, String serviceURL, String username, String password) {
		this.httpClient = httpClient;
		this.serviceURL = serviceURL;
		this.authentication = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public Profile analyze(String party, List<Post> posts) {
		logger.debug("Personality insights requested for: {}", party);

		List<ContentItem> contentItems =  posts.stream().sequential().map(new Function<Post, ContentItem>() {
			@Override
			public ContentItem apply(Post p) {
				return new ContentItem(p.getId(), party, (int) p.getCreated().getTime(), p.getMessage());
			}
		}).collect(Collectors.toList());
		
		ContentListContainer container = new ContentListContainer();
		container.setContentItems(contentItems);
		
		Profile profile = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Personality insights request: " + new ObjectMapper().writeValueAsString(container));
			}

			UriBuilder builder = UriBuilder.fromUri(serviceURL + "/v2/profile");
			WebTarget target = httpClient.target(builder);
			Response response = target.request().accept(MediaType.APPLICATION_JSON_TYPE).
			header(HttpHeaders.AUTHORIZATION, "Basic " + authentication).header(HttpHeaders.CONTENT_TYPE, "text/plain")
			.post(Entity.entity(container,Variant.encodings("UTF-8").mediaTypes(MediaType.APPLICATION_JSON_TYPE).build().get(0)));
			
			if (response.getStatus() == Status.OK.getStatusCode()) {
				profile = response.readEntity(Profile.class);
				if (logger.isDebugEnabled()) {
					logger.debug("Profile analysis complete for: " + party + "\n" + new ObjectMapper().writeValueAsString(profile));
				}
			} else {
				InputStream stream = (InputStream) response.getEntity();
				logger.error("Profile analysis failed: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
			}
		} catch(IOException e) {
			logger.error("Profile analysis failed with exception:" , e);
		}

		return profile;
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
