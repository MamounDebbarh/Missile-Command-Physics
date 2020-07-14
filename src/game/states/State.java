package game.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import game.DrawEngine;
import game.Config;
import game.IDrawable;
import objects.buildings.Base;
import objects.particles.BlackHole;
import objects.particles.BHMissile;
import objects.particles.Bomber;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.PhysicsStep;

import processing.core.PApplet;


//import processing.sound.*;

/**
 * This class represents a state of the game (i.e, playing, game over, end of wave etc.)
 * This allows the Controller to call the same update() and handleInput() functions for every state
 * and let each state's implementation decide what to do on those steps.
 * 
 * update() and handleInput() return the next state after the update or input.
 */
public abstract class State {

	public Game context;
	public DrawEngine drawEngine;

	protected PApplet parent;
	AudioPlayer player;



	State(Game context, DrawEngine drawEngine) {
		this.context = context;
		this.drawEngine = drawEngine;
		this.parent = drawEngine.parent;
		//	public SoundFile pew;
		Minim minim = new Minim(parent);
		this.player = minim.loadFile("data/Photon.wav");
	}
	
	/**
	 * Draw the state to the screen.
	 */
	public abstract void display();
	
	
	/**
	 * Updates that happen on each frame in the state.
	 * @return the next state of the game
	 */
	public abstract State update();
	
	
	/**
	 * Handle player input.
	 * @param input - Player input which contains mouse coordinates, mouse click and key pressed.
	 * @return the next state of the game.
	 */
	public abstract State handleInput(Input input);
	
	
	/**
	 * Function for updating the score.
	 * Different states may change the score differently or choose not to update the score at all.
	 * @param score
	 */
	public abstract void updateScore(int score);


/*
 * Common State functions.
 * These are functions that multiple GameStates use.
 */

	/**
	 * Check if all cities are destroyed.
	 * @return - Over if the game is over. 'this' otherwise.
	 */
	State checkGameOver() {
    	for (Base c : context.cities) {
    		if (c.destroyed) context.cityCount--;
    	}
    	
    	context.info.citiesLeft = context.cityCount;
    	
		if (context.cityCount <= 0) {
			return new Over(context, drawEngine);
		}
		else {
			context.cityCount = context.cities.size();
			return this;
		}
    }
	
	
	/**
	 * Display most of the game's objects and score information.
	 */
	void displayGame() {
		drawEngine.displayGame(context);
		drawEngine.displayInfo(context.info, context.level);
	}
	
	
	/**
	 * Update step of the running game to update object positions.
	 */
	void runningStep() {
    	PhysicsStep meteorStep = Meteor.getStep(context);
    	PhysicsStep missileStep = Missile.getStep(context);
    	PhysicsStep explosionStep = Explosion.getStep(context);
    	PhysicsStep blackholeMissileStep = BHMissile.getStep(context);
    	PhysicsStep bomberStep = Bomber.getStep(this.getClass(), context);

        context.physicsEngine.step(meteorStep, missileStep, explosionStep, blackholeMissileStep, bomberStep);

        if (context.level.levelNumber > Config.METEOR_SPLIT_STARTING_LEVEL) splitMeteors();
        
	    destroyObjects();
	}
	
	
	private void splitMeteors() {
		Random r = new Random();
		ArrayList<Meteor> newMeteors = new ArrayList<Meteor>();
		Iterator<Meteor> it = context.meteors.iterator();
		
		while (it.hasNext()) {
			Meteor m = it.next();
			
			/* Only split if the meteor is at a certain y coordinate range and large enough */
			if (m.radius >= Config.METEOR_SPLIT_MIN_RADIUS && m.position.y < Config.METEOR_SPLIT_MAX_HEIGHT && m.position.y > Config.METEOR_SPLIT_MIN_HEIGHT) {
				if (r.nextInt(Config.METEOR_SPLIT_RATE) == 0) {
					int numChildren = 2 + r.nextInt(1 + context.level.levelNumber/ Config.METEOR_SPLIT_LEVEL);
					for (int i = 0; i < numChildren; i++) {
						Meteor child = new Meteor(m.position.x, m.position.y, parent.random(-2f, 2f), parent.random(-0.5f, -2f), m.mass/2f);
						newMeteors.add(child);
					}
					context.meteorCount--;
					it.remove();
				}
			}
		}
		
		for (Meteor child : newMeteors) {
			context.meteorCount++;
    	    context.meteors.add(child);
    	    context.physicsEngine.registerNewParticle(child);
		}
	}

	/**
	 * Clean up of each step to destroy any objects and forces that should be destroyed after they've bee updated.
	 */
	private void destroyObjects() {
	    destroy(m -> (m.position.y > Config.GROUND_HEIGHT), context.meteors.iterator(), true);
	    destroy(m -> m.destroyed, context.missiles.iterator(), true);
	    destroy(e -> e.lifespan <= 0, context.explosions.iterator(), true);

	    destroy(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > Config.SCREEN_X || m.position.y + m.radius < 0), context.meteors.iterator(), false);
	    
	    /* Destroy meteors in black holes, but do not leave explosions */
	    for (BlackHole bh : context.blackholes)	destroy(m -> m.checkCollision(bh) != null, context.meteors.iterator(), false);
	    
	    /* Remove from the list without adding explosions */
	    remove(bhm -> bhm.destroyed, context.bhms.iterator());
	    remove(bh -> bh.lifespan <= 0, context.blackholes.iterator());
	    remove(ff -> ff.lifespan <= 0, context.forcefields.iterator());
    }
	
	/**
	 * Destroy all particles that pass the filter. These particles may add explosions to the game. 
	 * @param filter - filter function for which particles should be destroyed
	 * @param it - iterator of the list of particles
	 * @param explode - whether or not there should be an explosion when the particle is destroyed
	 */
    private <T extends Particle> void destroy(Function<T, Boolean> filter, Iterator<T> it, boolean explode) {
    	while (it.hasNext()) {
    		T object = it.next();
    	    if(filter.apply(object)) {
    	    	it.remove();
    	    	Explosion ex = object.destroy();
    	    	if (ex != null && explode) context.explosions.add(ex);
    	    	
    	    	/* Update score for destroyed meteors */
    	    	if (object.getClass().equals(Meteor.class)) {
    	    		context.meteorCount--;
    	    		if (explode) updateScore(Config.METEOR_EXPLODE_SCORE);
    	    		else updateScore(Config.METEOR_REMOVED_SCORE);
    	    	}
    	    }
    	}
    }
    
    /**
     * Only remove from the iterator list and nothing else
     * @param filter - filter function for which objects should be destroyed
     * @param it - iterator for the list of objects
     */
    private <T extends IDrawable> void remove(Function<T, Boolean> filter, Iterator<T> it) {
    	while (it.hasNext()) {
    		T object = it.next();
    		if (filter.apply(object)) {
    			it.remove();
    		}
    	}
    }
    
    
    Start newGame() {
		Game newGame = new Game();
		return new Start(newGame, drawEngine);
    }
}
