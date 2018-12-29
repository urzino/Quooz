
package quooz.shared.question;

import java.io.Serializable;

/**
 * The Enum Level represents both the player's level and the question's level.
 */
public enum Level implements Serializable {
	ONE, TWO, THREE;

	/**
	 * Increases the level.
	 *
	 * @return the level
	 */
	public Level increase() {
		switch (this) {
		case ONE:
			return TWO;
		case TWO:
			return THREE;
		case THREE:
			return THREE;
		}
		return null;
	}

	/**
	 * Decrease the level.
	 *
	 * @return the level
	 */
	public Level decrease() {
		switch (this) {
		case THREE:
			return TWO;
		case TWO:
			return ONE;
		case ONE:
			return ONE;
		}
		return null;
	}

	/**
	 * Converts the Enumeration Level to Int.
	 *
	 * @return the converted int
	 */
	public int toInt() {
		switch (this) {
		case ONE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		}
		return -1;
	}

	/**
	 * Gets the Enumeration Level from an integer.
	 *
	 * @param level the level in integer
	 * @return the converted Enumeration level
	 */
	public static Level getLevelFromInt(int level) {
		switch (level) {
		case 1: {
			return ONE;
		}
		case 2: {
			return TWO;
		}
		case 3: {
			return THREE;
		}
		}
		return null;
	}
}
