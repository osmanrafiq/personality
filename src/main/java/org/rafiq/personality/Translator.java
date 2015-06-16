package org.rafiq.personality;

import io.dropwizard.lifecycle.Managed;

/**
 * The {@link Translator} interface provides the means to translate a given text from one language to the other.
 */
public interface Translator extends Managed{

	/**
	 * Attempts to determine the language of the provided text
	 * @param text the text
	 * @return the determined language or null
	 */
	String determineLanguage(String text);
	
	/**
	 * Translates the provided text assumming the provided from language to the to language
	 * @param text the text to translate
	 * @param fromLanguage the language of the text
	 * @param toLanguage the language to translate the text into
	 * @return the translated text or null if text could not be translated
	 */
	String translate(String text, String fromLanguage, String toLanguage);
}
