package physics.forces;

import java.util.ArrayList;
import java.util.Iterator;

import objects.particles.Particle;


public class ForceRegistry {

	public ArrayList<ForceRegistration> registrations;
	
	public ForceRegistry() {
		registrations = new ArrayList<ForceRegistration>();
	}
	
	public void register(Particle particle, ForceGenerator forceGenerator) {
		registrations.add(new ForceRegistration(particle, forceGenerator));		
	}
	
	public void updateForces() {
		for (Iterator<ForceRegistration> it = registrations.iterator(); it.hasNext();) {
			ForceRegistration fr = it.next();
			Particle p = fr.particle;
			
			/* Remove forces that have expired from the registry */
			if (p.destroyed || fr.forceGenerator.lifespan <= 0) {
				it.remove();
			}
			else fr.forceGenerator.updateForce(p);
		}	
	}
	
	public void clear() {
		registrations.clear();
	}
	
}
