package game;

import objects.Crosshair;

import processing.core.PApplet;

public class MissileCommand extends PApplet {
	
	private Controller controller;
	public DrawEngine drawEngine;

	public void settings() {
		size(Config.SCREEN_X, Config.SCREEN_Y);
	}
	
	public void setup() {
	    drawEngine = new DrawEngine(this);
	    controller = new Controller(this, drawEngine);
		frameRate(60);
	}

	
	public void draw() {
		line(0, 300, 600, 300);
		controller.step();
		Crosshair crosshair = new Crosshair();
		crosshair.display(drawEngine);
	}


	public void mousePressed() {
		controller.handleInput(mouseX, mouseY, mouseButton, 0);
	}
	
	public void keyPressed() {
		controller.handleInput(mouseX, mouseY, 0, keyCode);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.MissileCommand");
	}
	
}
