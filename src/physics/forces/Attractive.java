package physics.forces;

import objects.particles.BlackHole;
import objects.particles.Particle;
import physics.forces.ForceGenerator;
import processing.core.PVector;

public class Attractive extends ForceGenerator {

	public static final float GRAV_CONSTANT = 2f;
	
	public Particle attractor;
	
	public Attractive(Particle attractor) {
		this.attractor = attractor;
		this.lifespan = BlackHole.BLACKHOLE_LIFESPAN;
	}
	
	@Override
	public void updateForce(Particle attractee) {
		PVector force = getAttractionForce(attractee);
		attractee.addForce(force);		
	}
	
	/**
	 * Calculates the force of gravitational attraction.
	 * @param attractee - particle being attracted
	 * @return - force of the attraction
	 */
	protected PVector getAttractionForce(Particle attractee) {
		PVector force = PVector.sub(attractor.position, attractee.position);
		float distance = force.mag();
		force.normalize();
		
		float mag = (GRAV_CONSTANT * attractor.mass * attractee.mass) / (distance * distance);
		return force.mult(mag);
	}

}
