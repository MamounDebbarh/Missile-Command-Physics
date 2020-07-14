package game.states;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import game.DrawEngine;
import game.Config;
import objects.buildings.Base;
import objects.particles.BlackHole;
import objects.particles.Missile;
import processing.core.PConstants;
import processing.core.PVector;

public class Shop extends State {

    private WaveEnd prevState;

    public Shop(Game context, DrawEngine drawEngine, WaveEnd prevState) {
        super(context, drawEngine);
        this.prevState = prevState;
    }

    @Override
    public void display() {
        drawEngine.displayGame(context);
        drawEngine.displayInfo(context.info, context.level);
        drawShopScreen();
    }

    @Override
    public State update() {
        return this;
    }

    @Override
    public State handleInput(Input input) {
        Inventory info = context.info;
        switch (input.keyPressed) {
            case KeyEvent.VK_F:
                return prevState;

            case KeyEvent.VK_1:
                if (info.score >= Config.BLACK_HOLE_COST) {
                    info.score -= Config.BLACK_HOLE_COST;
                    info.blackholesLeft += 1;
                }
                break;

            case KeyEvent.VK_2:
                if (info.score >= Config.FORCEFIELD_COST) {
                    info.score -= Config.FORCEFIELD_COST;
                    info.forcefieldsLeft += 1;
                }
                break;

            case KeyEvent.VK_3:
                if (info.score >= Config.MISSILE_COST) {
                    info.score -= Config.MISSILE_COST;
                    info.missilesLeft += 1;
                }
                break;

            case KeyEvent.VK_4:
                if (info.score >= Config.BASE_COST && info.citiesLeft < Config.NUM_CITIES) {
                    info.score -= Config.BASE_COST;
                    rebuildCity();
                }
                break;

            case KeyEvent.VK_5:
                if (info.score >= Config.FRIENDLY_EXPLOSIONS_COST && !context.friendlyExplosions) {
                    info.score -= Config.FRIENDLY_EXPLOSIONS_COST;
                    context.friendlyExplosions = true;
                }
        }
        return this;
    }

    private void rebuildCity() {
        ArrayList<Base> destroyedCities = (ArrayList<Base>) context.cities.stream()
                .filter(c -> c.destroyed)
                .collect(Collectors.toList());

        Random r = new Random();
        Base base = destroyedCities.get(r.nextInt(destroyedCities.size()));

        base.rebuild();
        context.info.citiesLeft++;
    }

    private void drawShopScreen() {
        int textX = Config.SCREEN_X / 2;
        int textY = Config.SCREEN_Y / 4;

        drawEngine.drawText(16, "Welcome to the shop, press F to leave.", textX, textY, 0);

        int shopX = 150;
        int shopY = Config.SCREEN_Y / 2 - 75;

        /* Black hole */
        BlackHole blackhole = new BlackHole(new PVector(shopX, shopY));
        blackhole.display(drawEngine);
        drawEngine.drawText(12, "[1] Buy a blackhole for " + Config.BLACK_HOLE_COST, shopX + 115, shopY, 0);

        /* Force field */
        parent.ellipseMode(PConstants.CENTER);
        parent.fill(0, 0, 100, 100);
        parent.ellipse(shopX + 350, shopY, 50, 50);
        drawEngine.drawText(12, "[2] Buy a forcefield for " + Config.FORCEFIELD_COST, shopX + 465, shopY, 0);

        /* Missile */
        Missile missile = new Missile(shopX, shopY + 100, 0, 0, parent.color(255, 127, 80));
        missile.display(drawEngine);
        drawEngine.drawText(12, "[3] Buy a missile for " + Config.MISSILE_COST, shopX + 90, shopY + 100, 0);


        /* Base */
        Base base = new Base(shopX + 350, shopY + 100);
        base.display(drawEngine);
        drawEngine.drawText(12, "[4] Rebuild a base for " + Config.BASE_COST, shopX + 470, shopY + 100, 0);


        /* Player explosions */
        if (!context.friendlyExplosions) {
            Missile friendly = new Missile(shopX, shopY + 165, 0, 0, parent.color(255, 30, 30));
            friendly.display(drawEngine);
            drawEngine.drawText(12, "[5] Missile explosions don't destroy your own cities for " + Config.FRIENDLY_EXPLOSIONS_COST, shopX + 200, shopY + 165, parent.color(0));
        } else {
            Missile friendly = new Missile(shopX, shopY + 165, 0, 0, parent.color(255, 30, 30));
            friendly.display(drawEngine);
            drawEngine.drawText(12, "[5] Upgrade purchased" + Config.FRIENDLY_EXPLOSIONS_COST, shopX + 200, shopY + 165, parent.color(0));
        }


    }

    @Override
    public void updateScore(int score) {
    }

}
