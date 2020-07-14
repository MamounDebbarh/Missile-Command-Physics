package objects;

import game.DrawEngine;
import game.IDrawable;

import processing.core.PApplet;
import processing.core.PConstants;

public class Crosshair implements IDrawable {

	@Override
	public void display(DrawEngine drawEngine) {
		PApplet parent = drawEngine.parent;
		parent.cursor(PConstants.CROSS);
		
	}

}
