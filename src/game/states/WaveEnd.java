package game.states;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.Config;

import objects.buildings.Cannon;
import processing.core.PConstants;
// REndOfWaveState
public class WaveEnd extends State {

	private int bonusScore;
	
	WaveEnd(Game context, DrawEngine drawEngine, int bonusScore) {
		super(context, drawEngine);
		this.bonusScore = bonusScore;
		
		updateScore(bonusScore);
	}

	@Override
	public void display() {
		displayGame();
		
		int textX = Config.SCREEN_X/2;
		int textY = Config.SCREEN_Y/4;
		drawEngine.drawText(32, "Wave " + context.level.levelNumber + " finished.", textX, textY, 0);
		drawEngine.drawText(16, bonusScore + " bonus score for remaining cities and missiles.", textX, textY+50, 0);
		drawEngine.drawText(16, "Press F to go to shop", textX, textY+100, 0);
		drawEngine.drawText(16, "Press Enter to start next wave.", textX, textY+125, 0);

		for (Cannon cannon : context.cannons) {
			cannon.destroyed = false;
		}
	}

	@Override
	public State update() {
    	runningStep();

		return checkGameOver();
	}

	@Override
	public State handleInput(Input input) {
		switch(input.keyPressed) {
			case PConstants.ENTER:
			case PConstants.RETURN:
				return nextLevel();
				
			case KeyEvent.VK_F:
				return new Shop(context, drawEngine, this);
				
			default:
				return this;
		}
	}
	
	private Play nextLevel() {
    	context.level.next();
    	context.info.resetWaveStart(context.level.levelNumber);
		context.meteorCount = context.level.meteorSpawnCount;
		return new Play(context, drawEngine);
	}

	@Override
	public void updateScore(int score) {
		context.info.missilesLeft = 0;
		context.info.score += score;
	}

}
