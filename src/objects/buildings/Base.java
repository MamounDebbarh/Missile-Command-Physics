package objects.buildings;

import game.DrawEngine;

import processing.core.PVector;

public class Base extends Building{
 
    public float radius = 30f;

    public Base(int posX, int posY) {
        position = new PVector(posX, posY);
        destroyed = false;
    }
   
    @Override
    public void display(DrawEngine drawEngine) {
        if (!destroyed) {
        	drawEngine.drawCityImage( position.x, position.y, 60, 60);
        } 
    }

	@Override
	public void destroy() {
		destroyed = true;
	}
	
	public void rebuild() {
		destroyed = false;
	}
    
}