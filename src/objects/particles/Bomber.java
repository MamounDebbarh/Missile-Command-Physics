package objects.particles;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import game.DrawEngine;
import game.states.Game;
import game.states.State;
import game.states.Play;
import physics.PhysicsEngine;
import physics.PhysicsStep;

public class Bomber extends Particle {

	public static final int BOMB_SPAWN = 220;
	public static final float BOMB_RADIUS = 10;
	public static final float BOMB_MASS = 0.8f;
	public static final float BOMB_SPEED_BASE = 2f;
	public static final float BOMB_SPEED_MULTIPLIER = 2f;
	
	public Bomber(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
		super(xPos, yPos, xVel, yVel, radius, 1);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawEngine.drawBomberImage( position.x, position.y, 60, 70);
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void spawnBomb(ArrayList<Meteor> meteors, PhysicsEngine physicsEngine, Random r) {
		float xVel = r.nextFloat()-1f;
		float yVel = BOMB_SPEED_BASE + r.nextFloat() * BOMB_SPEED_MULTIPLIER;
		
		Meteor bomb = new Meteor(position.x, position.y, xVel, yVel, BOMB_RADIUS, BOMB_MASS, 60);
		meteors.add(bomb);
		physicsEngine.registerNewParticle(bomb);
	}

	public static PhysicsStep getStep(Class<? extends State> stateClass, Game context) {
		return new PhysicsStep(context.bombers, new Function<Bomber, Void>() {
			
			@Override
			public Void apply(Bomber b) {
				b.integrate();
				
				Random r = new Random();
				if (r.nextInt(BOMB_SPAWN) == 0 && stateClass.equals(Play.class)) {
					b.spawnBomb(context.meteors, context.physicsEngine, r);
					context.meteorCount++;
				}
				
				return null;
			}
			
		});
	}

}
