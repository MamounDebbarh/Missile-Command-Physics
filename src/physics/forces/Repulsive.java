package physics.forces;

import objects.particles.Particle;
import processing.core.PVector;

public class Repulsive extends Attractive {

	public Repulsive(Particle attractor) {
		super(attractor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateForce(Particle attractee) {
		PVector force = getAttractionForce(attractee).mult(-1);
		attractee.addForce(force);
	}

}
