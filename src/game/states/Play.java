package game.states;

import java.util.Random;

import game.DrawEngine;
import game.Config;
import objects.buildings.Cannon;
import objects.particles.BHMissile;
import objects.particles.Bomber;
import objects.particles.ForceField;
import objects.particles.Meteor;
import objects.particles.Missile;

import processing.core.PConstants;

public class Play extends State {

    private Random random;

    Play(Game context, DrawEngine drawEngine) {
        super(context, drawEngine);

        this.random = new Random();
    }

    @Override
    public void display() {
        displayGame();
    }

    /**
     * Method to spawn the meteors with random starting velocity and position
     */
    private void spawnMeteor() {
        if (random.nextInt(Config.METEOR_SPAWN_RATE_INV) == 0) {
            float xPos = parent.random(100, Config.SCREEN_X - 100);
            float yPos = 0;
            float xVel = parent.random(-2f, 2f);
            float yVel = parent.random(0, 2f);
            float mass = parent.random(0.1f, 0.5f) + context.level.meteorMassBase;

            Meteor meteor = new Meteor(xPos, yPos, xVel, yVel, mass);
            context.level.spawnMeteor();
            context.meteors.add(meteor);
            context.physicsEngine.registerNewParticle(meteor);
        }
    }

    /**
     * Method to spawn the Bomber
     */
    private void spawnBomber() {
        if (random.nextInt(Config.BOMBER_SPAWN_RATE_INV) == 0 && context.level.numBombers > 0) {
            Bomber bomber = new Bomber(0, parent.random(50, 200), 1f, parent.random(-0.2f, 0.2f), 0f, 0);
            context.level.spawnBomber();
            context.bombers.add(bomber);
            /* For simplicity, bombers are not affected by any forces, so do not add to the force registry. */
        }
    }

    @Override
    public State update() {


        if (context.level.finished && context.meteorCount <= 0) {
            int bonusCityScore = context.info.citiesLeft * Config.BASE_SURVIVE_SCORE;
            int bonusMissileScore = context.info.missilesLeft * Config.MISSILE_LEFT_SCORE;
            return new WaveEnd(context, drawEngine, bonusCityScore + bonusMissileScore);
        }

        if (!context.level.finished) {
            spawnMeteor();
            spawnBomber();
        }

        runningStep();

        return checkGameOver();
    }

    @Override
    public State handleInput(Input input) {
        float mouseX = input.mouseX;
        float mouseY = input.mouseY;

        Cannon cannon = context.getClosestCannon((int) mouseX, (int) mouseY);

        if (mouseY < Config.GROUND_HEIGHT) {
            if (input.mouseButton == PConstants.LEFT && context.info.missilesLeft > 0 && cannon != null && !cannon.destroyed) {
                try{
                    player.rewind();
                    player.play();
                } catch (Exception e){
                    System.out.println("OKAY");
                }
                context.info.missilesLeft--;
                context.missiles.add(new Missile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY, context.friendlyExplosions));
            } else if (input.mouseButton == PConstants.RIGHT && context.info.blackholesLeft > 0) {
                context.info.blackholesLeft--;
                context.bhms.add(new BHMissile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY));
            } else if (input.mouseButton == PConstants.CENTER && context.info.forcefieldsLeft > 0) {
                context.info.forcefieldsLeft--;
                context.forcefields.add(new ForceField(mouseX, mouseY));
            }
        }

        return this;
    }

    @Override
    public void updateScore(int score) {
        this.context.info.score += score;
    }

}
