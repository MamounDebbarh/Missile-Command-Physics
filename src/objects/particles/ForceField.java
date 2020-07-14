package objects.particles;

import game.DrawEngine;
import physics.forces.Repulsive;

public class ForceField extends Particle {

	private static final int FORCEFIELD_MASS = 100;
	private static final int FORCEFIELD_LIFESPAN = 80;
	private static final int FORCEFIELD_RADIUS = 30;
	
	
	public int lifespan;
	public Repulsive repulsiveForce;
	
	public ForceField(float xPos, float yPos) {
		super(xPos, yPos, 0, 0, FORCEFIELD_RADIUS, FORCEFIELD_MASS);
		this.lifespan = FORCEFIELD_LIFESPAN;

		repulsiveForce = new Repulsive(this);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		repulsiveForce.lifespan--;
		lifespan--;
		
		int col = drawEngine.parent.color(0, 0, 100, 100);
		drawEngine.drawEllipse(col, position.x, position.y, radius * 2, radius * 2);
		
		radius += 10f;
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
