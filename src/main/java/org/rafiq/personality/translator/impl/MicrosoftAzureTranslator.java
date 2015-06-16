package org.rafiq.personality.translator.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.IOUtils;
import org.rafiq.personality.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class MicrosoftAzureTranslator implements Translator{
	private static final Logger logger = LoggerFactory.getLogger(MicrosoftAzureTranslator.class);

	private final Client httpClient;
	private final String clientID;
	private final String clientSecret;
	private final URLCodec codec;
	
	private MicrosoftAzureAccessToken accessToken;

	public MicrosoftAzureTranslator(Client httpClient, String clientID, String clientSecret) {
		this.httpClient = httpClient;
		this.clientID = clientID;
		this.clientSecret = clientSecret;
		this.codec = new URLCodec("UTF-8");
	}
	
	@Override
	public String determineLanguage(String text) {
		MicrosoftAzureAccessToken accessToken = getAccessToken();
		// TODO Auto-generated method stub
		return "da";
	}

	@Override
	public String translate(String text, String fromLanguage, String toLanguage) {
		String translation = null;

		MicrosoftAzureAccessToken accessToken = retrieveAccessToken();
		if (accessToken != null) {
			logger.info("Translation requested: " + text + ", from: " + fromLanguage + ", to: " + toLanguage);

			try {
				UriBuilder builder = UriBuilder.fromUri("http://api.microsofttranslator.com/v2/Http.svc/Translate?text=%22{text}%22&from={from}&to={to}");
				String textToTranslate = text.replaceAll("\\n", " ");
				builder.resolveTemplate("text", textToTranslate);
				builder.resolveTemplate("from", fromLanguage);
				builder.resolveTemplate("to", toLanguage);

				WebTarget target = httpClient.target(builder);
				Builder requestBuilder = target.request().accept(MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getAccessToken());
				Response response = requestBuilder.get();

				if (response.getStatus() == Status.OK.getStatusCode() ) {
					String xmlResponse = null;
					InputStream stream = (InputStream) response.getEntity();
					xmlResponse = IOUtils.toString(stream, StandardCharsets.UTF_8);

					logger.info("Translation response: " + xmlResponse);

					XPath xPath = XPathFactory.newInstance().newXPath();
					translation = xPath.evaluate("/", new InputSource(new StringReader(xmlResponse)));
					logger.info("Translated text: " + translation);
				} else {
					InputStream stream = (InputStream) response.getEntity();
					logger.error("Translation failed: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
				}

			} catch (IOException | ProcessingException | XPathExpressionException e ) {
				logger.error("Unable to retrieve translation", e);
			}
		}

		return translation;
	}

	private synchronized MicrosoftAzureAccessToken getAccessToken() {
		if (accessToken == null || accessToken.isExpired()) {
			accessToken = retrieveAccessToken();
		}
		
		return accessToken;
	}
	
	private MicrosoftAzureAccessToken retrieveAccessToken() {
		MicrosoftAzureAccessToken accessToken = null;

		try {
			String tokenRequest = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s&scope=http://api.microsofttranslator.com", codec.encode(clientID), codec.encode(clientSecret), codec.encode("http://api.microsofttranslator.com"));

			logger.info("Requesting new access token: " + tokenRequest);
			
			WebTarget target = httpClient.target("https://datamarket.accesscontrol.windows.net/v2/OAuth2-13/");
			Response httpResponse = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(tokenRequest, Variant.encodings("UTF-8").mediaTypes(MediaType.APPLICATION_FORM_URLENCODED_TYPE).build().get(0)));
			
			
			if (httpResponse.getStatus() == Status.OK.getStatusCode()) {
				accessToken = httpResponse.readEntity(MicrosoftAzureAccessToken.class);
				logger.info("Azure Access Token Retrieved:\n" + accessToken);
			} else {
				InputStream stream = (InputStream) httpResponse.getEntity();
				logger.error(IOUtils.toString(stream, StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			logger.error("Token request failed", e);
		}

		return accessToken;
	}
	
	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() throws Exception {
		httpClient.close();
	}
}
