package game;

import java.util.Random;

/**
 * Represents every wave of the game, keeps track of the number of meteors to spawn and adjusts the difficulty every level.
 */
public class Level {

	private static final int STARTING_METEORS = 10;
	private static final int METEOR_INCREASE_PER_LEVEL = 3;
	private static final float MASS_INCREASE_PER_LEVEL = 0.1f;
	
	private static final int LEVEL_INCREASE_MASS = 7;
	
	private static final int LEVEL_INCREASE_BOMBER = 6;
	
	private static final int LEVEL_INCREASE_METEOR = 5;
	
	public boolean finished; 
	
	public int levelNumber;
	public int meteorSpawnCount;
	private int numMeteors;
	
	public int numBombers;
	private int bomberSpawnCount;

	public float meteorMassBase;
	
	public Level() {
		this.levelNumber = 1;
		this.meteorMassBase = 0.2f;
		
		this.meteorSpawnCount = STARTING_METEORS;
		this.numMeteors = meteorSpawnCount;
		
		this.bomberSpawnCount = 0;

		this.finished = false;
	}



	public void next() {
		Random random = new Random();
		levelNumber++;

		if (levelNumber % LEVEL_INCREASE_MASS == 0) meteorMassBase += MASS_INCREASE_PER_LEVEL;
		if (levelNumber > LEVEL_INCREASE_METEOR) meteorSpawnCount += random.nextInt(levelNumber);
		meteorSpawnCount += random.nextInt(METEOR_INCREASE_PER_LEVEL);
		numMeteors = meteorSpawnCount;
		
		if (levelNumber % LEVEL_INCREASE_BOMBER == 0) bomberSpawnCount += 1;
		numBombers = bomberSpawnCount;
		
		finished = false;
	}


	public void spawnMeteor() {
		numMeteors--;
		if (numMeteors <= 0) finished = true;
	}
	
	public void spawnBomber() {
		if (numBombers > 0) numBombers--;
	}
	
}
