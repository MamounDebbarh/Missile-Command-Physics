package physics.forces;

import objects.particles.Explosion;
import objects.particles.Particle;
import physics.forces.ForceGenerator;
import processing.core.PVector;

public class Explosive extends ForceGenerator{

	private static final float EXPLOSIVE_FORCE = 1.9f;
	
	public PVector position;
	public float radius;
	
	public Explosive(PVector position, float radius) {
		this.position = position;
		this.radius = radius;
		
		this.lifespan = Explosion.EXPLOSION_LIFESPAN/2;
	}
	
	public Explosive(PVector position, float radius, int lifespan) {
		this(position, radius);
		this.lifespan = lifespan;
	}
	
	@Override
	public void updateForce(Particle particle) {
		lifespan--;
		if (PVector.dist(particle.position, position) < radius + particle.radius) {
			PVector impulseDirection = PVector.sub(position, particle.position).mult(-1);
			float impulseMag = EXPLOSIVE_FORCE * (1f/impulseDirection.mag()) * (1f/impulseDirection.mag());
	
			particle.addForce(impulseDirection.mult(impulseMag));
		}
	}


}
