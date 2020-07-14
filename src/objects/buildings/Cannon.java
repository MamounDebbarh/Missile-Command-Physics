package objects.buildings;

import game.DrawEngine;

import processing.core.PVector;

public class Cannon extends Building {

    public float radius = 15f;

    public Cannon(int posX, int posY) {
        position = new PVector(posX, posY);
    }
    
    @Override
    public void display(DrawEngine drawEngine) {
        if (!destroyed) {
            drawEngine.drawCannonImage(position.x, position.y, 60, 60);
        } else {
            drawEngine.drawCannonDisImage(position.x, position.y, 60, 60);

        }
    }

	@Override
	public void destroy() {
        destroyed = true;
	}
    
}