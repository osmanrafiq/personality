package org.rafiq.personality;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class PersonalityConfiguration extends Configuration{
	@Valid
	@NotNull
	@JsonProperty
	private FacebookClientFactory facebook = new FacebookClientFactory();
	
	@Valid
	@NotNull
	@JsonProperty
	private TranslatorFactory translator = new TranslatorFactory();

	@Valid
	@NotNull
	@JsonProperty
	private WatsonClientFactory watson = new WatsonClientFactory();
	
	@Valid
	@NotNull
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();
	
	@Valid
	@NotEmpty
	@JsonProperty
	private List<String> parties = new ArrayList<String>();
	
	public FacebookClientFactory getFacebook() {
		return facebook;
	}

	public TranslatorFactory getTranslator() {
		return translator;
	}

	public WatsonClientFactory getWatson() {
		return watson;
	}
	
	public DataSourceFactory getDatabase() {
		return database;
	}
	
	public List<String> getParties() {
		return parties;
	}
}