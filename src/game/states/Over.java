package game.states;

import game.DrawEngine;
import game.Config;

import processing.core.PConstants;

public class Over extends State {

	Over(Game context, DrawEngine engine) {
		super(context, engine);
	}



	@Override
	public State handleInput(Input input) {
		if (input.keyPressed == PConstants.RETURN || input.keyPressed == PConstants.ENTER) {
			return newGame();
		}
		return this;
	}

	@Override
	public State update() {
		runningStep();
		return this;
	}
	@Override
	public void updateScore(int score) {
		/* Don't update score on game over state */		
	}

	@Override
	public void display() {
		displayGame();
		int textX = Config.SCREEN_X/2;
		int textY = Config.SCREEN_Y/3;
		drawEngine.drawText(32, "Game Over", textX, textY, 0);
		drawEngine.drawText(32, "Final score: " + context.info.score, textX, textY + 50, 0);
		drawEngine.drawText(16, "Press Enter to play again.", textX, textY + 100, 0);
	}

}
