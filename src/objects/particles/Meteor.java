package objects.particles;

import java.util.ArrayList;
import java.util.function.Function;

import game.DrawEngine;
import game.Config;
import game.states.Game;
import physics.Collision;
import physics.PhysicsStep;
import processing.core.PVector;

public class Meteor extends Particle {

    ArrayList<PVector> trail = new ArrayList<>();
    int trailSize = 60;

    public static final int METEOR_RADIUS_BASE = 20;

    public Meteor(float xPos, float yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel, METEOR_RADIUS_BASE * mass, mass);
    }

    public Meteor(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
        super(xPos, yPos, xVel, yVel, radius, mass);
    }

    public Meteor(float xPos, float yPos, float xVel, float yVel, float radius, float mass, int col) {
        super(xPos, yPos, xVel, yVel, radius, mass);
        this.col = col;
    }

    @Override
    public void display(DrawEngine drawEngine) {
        if (!destroyed) {
            trail.add(new PVector(position.x, position.y));
            float size = radius * 2;
            col = drawEngine.parent.color(0, 204, 0, 200);


            for (int i = 0; i < trail.size()-2; i++) {
                PVector currentTrail = trail.get(i);
                PVector previousTrail = trail.get(i + 1);

                drawEngine.parent.stroke(255, 255*i/trail.size());
                drawEngine.parent.line(
                        previousTrail.x, previousTrail.y,
                        currentTrail.x, currentTrail.y
                );
            }

            if (trail.size() >= trailSize) {
                trail.remove(0);
            }

            drawEngine.drawEllipse(col, position.x, position.y, size, size);


        }
    }

    public Explosion destroy() {
        if (!destroyed) {
            destroyed = true;
            position.y = Config.GROUND_HEIGHT;
            return new Explosion(position, radius, false);
        }
        return null;
    }

    public Explosion remove() {
        destroyed = true;
        return null;
    }


    public static PhysicsStep getStep(Game context) {
        return new PhysicsStep(context.meteors, new Function<Meteor, Void>() {

            @Override
            public Void apply(Meteor me) {
                me.integrate();

                for (Meteor m : context.meteors) {
                    Collision c = m.checkCollision(me);
                    if (c != null) {
                        context.physicsEngine.collisions.add(c);
                    }
                }

                for (BlackHole bh : context.blackholes) {
                    context.physicsEngine.forceRegistry.register(me, bh.attractionForce);
                }

                for (ForceField ff : context.forcefields) {
                    context.physicsEngine.forceRegistry.register(me, ff.repulsiveForce);
                }
                return null;
            }
        });
    }


}