package objects.particles;

import java.util.function.Function;

import game.states.Game;
import physics.PhysicsStep;
import processing.core.PApplet;

public class BHMissile extends Missile {

	public BHMissile(PApplet parent, float xPos, float yPos, float destX, float destY) {
		super(xPos, yPos, destX, destY, parent.color(0));
	}

	
	public static PhysicsStep getStep(Game context) {
		return new PhysicsStep(context.bhms, (Function<BHMissile, Void>) bhm -> {
			if (bhm.destroyed) {
				context.blackholes.add(new BlackHole(bhm.position));
			}
			else {
				bhm.integrate();
			}

			return null;
		});
	}
	
}
