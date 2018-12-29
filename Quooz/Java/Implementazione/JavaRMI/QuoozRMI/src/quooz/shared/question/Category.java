
package quooz.shared.question;

import java.io.Serializable;

/**
 * The Enum Category represents a question's category.
 */
public enum Category implements Serializable {
	ART, HISTORY, SPORT, SCIENCE, ENTERTAINMENT, GEOGRAPHY;

	/**
	 * Translates to italian a category.
	 *
	 * @return the translated string
	 */
	public String toIta() {
		switch (this) {
		case ART: {
			return "ARTE";
		}
		case HISTORY: {
			return "STORIA";
		}
		case SPORT: {
			return "SPORT";
		}
		case SCIENCE: {
			return "SCIENZE";
		}
		case ENTERTAINMENT: {
			return "INTRATTENIMENTO";
		}
		case GEOGRAPHY: {
			return "GEOGRAFIA";
		}
		}
		return "";
	}

	/**
	 * Translates the category from Italian to English.
	 *
	 * @param category the category in italian
	 * @return the Translated Category
	 */
	public static Category getCategoryFromIta(String category) {
		switch (category) {
		case "ARTE": {
			return ART;
		}
		case "STORIA": {
			return HISTORY;
		}
		case "INTRATTENIMENTO": {
			return ENTERTAINMENT;
		}
		case "GEOGRAFIA": {
			return GEOGRAPHY;
		}
		case "SPORT": {
			return SPORT;
		}
		case "SCIENZE": {
			return SCIENCE;
		}
		}
		return null;
	}

}
