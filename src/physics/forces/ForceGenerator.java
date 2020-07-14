package physics.forces;

import objects.particles.Particle;

public abstract class ForceGenerator {
	
	public int lifespan;
	
	public abstract void updateForce(Particle particle);

}
