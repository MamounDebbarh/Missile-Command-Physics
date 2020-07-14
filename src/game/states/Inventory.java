package game.states;

import java.util.Random;

import game.Config;

/**
 * Class to abstract over all game information that is displayed to the player.
 */
public class Inventory {

	public int score;
	public int missilesLeft;
	public int blackholesLeft;
	public int forcefieldsLeft;
	
	int citiesLeft;

	/**
	 * Get Player's inventory
	 * @param score - Score
	 * @param missilesLeft - Missiles left
	 * @param blackholesLeft - Blackholes left
	 * @param forcefieldsLeft - Forcefields left
	 * @param citiesLeft - Cities left
	 */
	Inventory(int score, int missilesLeft, int blackholesLeft, int forcefieldsLeft, int citiesLeft) {
		this.score = score;
		this.missilesLeft = missilesLeft;
		this.blackholesLeft = blackholesLeft;
		this.forcefieldsLeft = forcefieldsLeft;
		
		this.citiesLeft = citiesLeft;
	}

	void resetWaveStart(int levelNumber) {
		Random random = new Random();
		missilesLeft += Config.NUM_STARTING_MISSILES + levelNumber + random.nextInt(levelNumber);
		if (levelNumber % 4 == 0) blackholesLeft++;
		if (levelNumber % 6 == 0) forcefieldsLeft++;		
	}
	
}
