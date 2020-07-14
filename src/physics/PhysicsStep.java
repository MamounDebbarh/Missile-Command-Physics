package physics;

import java.util.ArrayList;
import java.util.function.Function;

import objects.particles.Particle;

public class PhysicsStep {

	public ArrayList<? extends Particle> particles;
	public Function step;
	
	public PhysicsStep(ArrayList<? extends Particle> particles, Function<? extends Particle, Void> function) {
		this.particles = particles;
		this.step = function;
	}

	public void apply() {
		for (Particle p : particles) {
			step.apply(p);
		}
	}
	
	
}
