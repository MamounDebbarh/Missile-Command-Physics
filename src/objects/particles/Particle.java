package objects.particles;

import processing.core.PVector;
import game.IDrawable;
import physics.Collision;

public abstract class Particle implements IDrawable {
  
    public boolean destroyed;
    
    public float radius;
    public float mass;
    public PVector position;
	public PVector velocity;
	
	public PVector forceAccumulator;
	
    public int col = 128;
    
    public Particle(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
        this.position = new PVector(xPos, yPos);
        this.velocity = new PVector (xVel, yVel);
        this.radius = radius;
        this.mass = mass;
        this.destroyed = false;
        forceAccumulator = new PVector(0, 0);
    }
    
    /**
     * Destroy this entity in the game.
     * Some particles may explode when destroyed and so return an Explosion.
     * If not they return null.
     */
    public abstract Explosion destroy();
    
    /**
     * Integrate step where velocity and forces are applied to the particle.
     */
    public void integrate() {
        position.add(velocity);
        
        PVector acceleration = forceAccumulator.copy();
        acceleration.mult(getInvMass());
        
        velocity.add(acceleration);

        /* Reset accumulator */
        forceAccumulator.set(0, 0);
    }
    
    public Collision checkCollision(Particle collider) {
        float collideDistance = radius + collider.radius;
        float distance = PVector.dist(position, collider.position);
        
        if (distance <= collideDistance) {
            return new Collision(this, collider, (distance < collideDistance));
        }
        
        return null;
    }

	public void addForce(PVector force) {
		forceAccumulator.add(force);	
	}
	
	public float getInvMass() {
		return (1f/mass);
	}

  
}