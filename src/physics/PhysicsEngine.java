package physics;
import java.util.ArrayList;

import objects.particles.Particle;
import physics.forces.Drag;
import physics.forces.Gravity;
import physics.forces.ForceRegistry;

/**
 * Class that deals with all the physics steps of the game.
 */
public class PhysicsEngine {

	public static final float dragk1 = 0.0003f;
	public static final float dragk2 = 0.0001f;
	
    public ArrayList<Collision> collisions;
    
    public ForceRegistry forceRegistry;
    public Gravity gravity;
    public Drag drag;
    

    public PhysicsEngine() {
    	collisions = new ArrayList<Collision>();
    	
    	forceRegistry = new ForceRegistry();
    	gravity = new Gravity();
    	drag = new Drag(dragk1, dragk2);

    }

    public void step(PhysicsStep... steps) {
    	forceRegistry.updateForces();
        resolveCollisions();
        
        for (PhysicsStep step : steps) step.apply();
    }
    
    
    public void resolveCollisions() {
        for (Collision c : collisions) c.resolveCollision();
        collisions.clear();
    }

	public void registerNewParticle(Particle p) {
		forceRegistry.register(p, gravity);
		forceRegistry.register(p, drag);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}