package physics.forces;

import objects.particles.Particle;
import physics.forces.ForceGenerator;
import processing.core.PVector;

public class Drag extends ForceGenerator{

	private float k1, k2;
	
	public Drag(float k1, float k2) {
		this.k1 = k1;
		this.k2 = k2;
		
		this.lifespan = 1;
	}
	
	@Override
	public void updateForce(Particle particle) {
		PVector velocity = particle.velocity.copy();
		float speed = velocity.mag();
		
		float dragCoeff = (k1 * speed) + (k2 * speed * speed);
		
		PVector resultingForce = velocity.normalize().mult(-dragCoeff);
		particle.addForce(resultingForce);
	}
	
	
}
