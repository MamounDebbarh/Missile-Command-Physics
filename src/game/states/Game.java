package game.states;

import java.util.ArrayList;

import game.Config;
import game.Level;
import objects.buildings.Cannon;
import objects.buildings.Base;
import objects.particles.BlackHole;
import objects.particles.BHMissile;
import objects.particles.Bomber;
import objects.particles.Explosion;
import objects.particles.ForceField;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysicsEngine;
import processing.core.PApplet;

/**
 * Class to abstract over all game data.
 */
public class Game {

    public PhysicsEngine physicsEngine;

    public ArrayList<Meteor> meteors;
    public ArrayList<Meteor> meteorToRemove;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bomber> bombers;

    public ArrayList<BHMissile> bhms;
    public ArrayList<BlackHole> blackholes;
    public ArrayList<ForceField> forcefields;

    public ArrayList<Base> cities;
    public ArrayList<Cannon> cannons;

    Level level;
    Inventory info;

    int cityCount;
    public int meteorCount;

    boolean friendlyExplosions;


    public Game() {
        initFields();
        initGameObjects(cities, cannons);

        this.cityCount = cities.size();
        this.meteorCount = level.meteorSpawnCount;
        this.friendlyExplosions = false;

        this.info = new Inventory(0, Config.NUM_STARTING_MISSILES, 0, 0, cityCount);
    }

    /**
     * Initialize the Arraylists
     */
    private void initFields() {
        this.meteors = new ArrayList<>();
        this.meteorToRemove = new ArrayList<>();
        this.missiles = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.bombers = new ArrayList<>();
        this.bhms = new ArrayList<>();
        this.blackholes = new ArrayList<>();
        this.forcefields = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.cannons = new ArrayList<>();

        this.physicsEngine = new PhysicsEngine();
        this.level = new Level();
    }


    /**
     * Initialize game objects
     * @param cities - adding the cities
     * @param cannons - adding the cannons
     */
    private void initGameObjects(ArrayList<Base> cities, ArrayList<Cannon> cannons) {
        for (int i = 0; i < Config.NUM_CITIES; i++) {
            cities.add(new Base(115 + ((Config.SCREEN_X / Config.NUM_CITIES) * i), Config.GROUND_HEIGHT - 10));
        }
        for (int i = 0; i < Config.NUM_CANNONS; i++) {
            cannons.add(new Cannon((int) (55 + ((Config.SCREEN_X / Config.NUM_CANNONS) * i * 1.15)), Config.GROUND_HEIGHT));
        }
    }


    public Cannon getClosestCannon(int posX, int posY) {
        Cannon closestCannon = null;
        float closestDistance = Integer.MAX_VALUE;

        for (Cannon cannon : cannons) {
            if (!cannon.destroyed) {
                float distance = PApplet.sqrt(PApplet.sq(cannon.position.x - posX) + PApplet.sq(cannon.position.y - posY));
                if (closestCannon == null || distance < closestDistance) {
                    closestCannon = cannon;
                    closestDistance = distance;
                }
            }
        }

        return closestCannon;
    }
}
