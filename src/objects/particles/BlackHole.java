package objects.particles;

import game.DrawEngine;
import physics.forces.Attractive;

import processing.core.PVector;

public class BlackHole extends Particle {

	public static final int BLACKHOLE_MASS = 150;
	public static final int BLACKHOLE_RADIUS = 30;
	public static final int BLACKHOLE_LIFESPAN = 110;
	
	public int lifespan;
	public Attractive attractionForce;
	
	public BlackHole(PVector position) {
		super(position.x, position.y, 0, 0, BLACKHOLE_RADIUS, BLACKHOLE_MASS);
		this.lifespan = BLACKHOLE_LIFESPAN;
		
		attractionForce = new Attractive(this);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		attractionForce.lifespan--;
		lifespan--;
		
		drawEngine.drawEllipse(0, position.x, position.y, radius * 2, radius * 2);
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
