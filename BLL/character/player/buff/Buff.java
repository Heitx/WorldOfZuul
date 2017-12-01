package BLL.character.player.buff;

import BLL.character.player.Player;

/**
 * A {@link Buff} can be assigned to the player, giving advantages or change variables on the player.
 * It also describes the requirements for a buff, what a buff must contain.
 */
public interface Buff {
	/**
	 * When applying/adding the buff to the player, this action will be invoked.
	 * @param player getting the buff
	 */
	void onApply(Player player);

	/**
	 * Every game tick (depending on GUI), this action will be invoked.
	 * @param player getting the buff
	 */
	void onGameTick(Player player);

	/**
	 * When removing the buff from the player, this action will be invoked.
	 * @param player having the buff
	 */
	void onEnd(Player player);

	/**
	 * Checks if the buff has expired.
	 * This function also contains how the buff should be removed (e.g. by time etc).
	 * @param player getting the buff
	 */
	boolean isExpired();
}
