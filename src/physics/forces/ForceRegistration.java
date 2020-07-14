package physics.forces;

import objects.particles.Particle;

public class ForceRegistration {

	public Particle particle;
	public ForceGenerator forceGenerator;
	
	public ForceRegistration(Particle particle, ForceGenerator forceGenerator) {
		this.particle = particle;
		this.forceGenerator = forceGenerator;	
	}
	
}
