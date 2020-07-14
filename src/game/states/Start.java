package game.states;

import game.DrawEngine;
import game.Config;
import processing.core.PConstants;

public class Start extends State {

	public Start(Game context, DrawEngine drawEngine) {
		super(context, drawEngine); }



	@Override
	public void display() {
		parent.noStroke();
		drawEngine.backStar.resize(Config.SCREEN_X, Config.SCREEN_Y);
		parent.background(drawEngine.backStar);
		drawEngine.drawText(64, "Start game", Config.SCREEN_X/2, Config.SCREEN_Y/2, 0);
		drawEngine.drawText(32, "Press Enter to start.", Config.SCREEN_X/2, Config.SCREEN_Y/2+75, 0);
	}

	@Override
	public State update() {
		return this;
	}

	@Override
	public State handleInput(Input input) {
		if (input.keyPressed == PConstants.ENTER || input.keyPressed == PConstants.RETURN) {
			parent.stroke(240);
			return new Play(context, drawEngine);
		}
		else return this;
	}

	@Override
	public void updateScore(int score) {

	}

}
