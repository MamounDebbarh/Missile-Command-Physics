package game;

import java.util.ArrayList;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import game.states.Game;
import game.states.Inventory;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;


public class DrawEngine {

    public PApplet parent;

    public PImage backStar;
    private PImage dCity;
    private PImage dCanon;
    private PImage dBomber;
    private PImage dRed;
    private AudioPlayer pBoom;






    public DrawEngine(PApplet parent) {
        this.parent = parent;
        this.backStar = parent.loadImage("design/Background.png");
        this.dCity = parent.loadImage("design/City.png");
        this.dCanon = parent.loadImage("design/Canon.png");
        this.dBomber = parent.loadImage("design/Bomber.png");
        this.dRed = parent.loadImage("design/red.png");
        Minim minim = new Minim(parent);
        this.pBoom= minim.loadFile("data/boom.wav");
    }


    /**
     * Draw the main game with the background, ground and all game objects.
     *
     * @param context - contextual information of the game to draw
     */
    public void displayGame(Game context) {
//        parent.background(255);
        backStar.resize(Config.SCREEN_X, Config.SCREEN_Y);
        parent.background(backStar);

        displayDrawables(context.meteors,
                context.missiles,
                context.explosions,
                context.bombers,
                context.bhms,
                context.blackholes,
                context.forcefields,
                context.cities,
                context.cannons);
    }

    @SafeVarargs
    private final void displayDrawables(ArrayList<? extends IDrawable>... drawables) {
        for (ArrayList<? extends IDrawable> drawList : drawables) {
            for (IDrawable drawable : drawList) {
                drawable.display(this);
            }
        }
    }


    /**
     * Generic draw text function for other classes to draw text to the screen
     *
     * @param textSize - size of the text
     * @param text     - text to be drawn
     * @param posX     - x position of the text
     * @param posY     - y position of the text
     * @param col      - colour of the text
     */
    public void drawText(int textSize, String text, int posX, int posY, int col) {
        PFont font = parent.createFont("Arial", textSize, true);

        parent.textFont(font, textSize);
        parent.fill(col);
        parent.text(text, posX, posY);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
    }

    /**
     * Draw an ellipse based on given parameters.
     *
     * @param col    - colour of the circle
     * @param xPos   - x coordinate of the circle
     * @param yPos   - y coordinate of the circle
     * @param width  - width of the circle
     * @param height - height of the circle
     */
    public void drawEllipse(int col, float xPos, float yPos, float width, float height) {
        parent.ellipseMode(PConstants.CENTER);
        parent.fill(col);
        parent.ellipse(xPos, yPos, width, height);
    }

    public void drawExplosion(int col, float xPos, float yPos, float width, float height) {
        pBoom.rewind();
        pBoom.play();
        parent.ellipseMode(PConstants.CENTER);
        parent.fill(col);
        parent.ellipse(xPos, yPos, width, height);
    }


    /**
     * Draw a Base
     *
     * @param xPos   - x coordinates of image
     * @param yPos   - y coordinates of image
     * @param width  - width of image
     * @param height - height of image
     */
    public void drawCityImage(float xPos, float yPos, float width, float height) {
        parent.image(dCity, xPos-25, yPos - (height / 2), width, height);
    }

    /**
     * Draw a Cannon
     *
     * @param xPos   - x coordinates of image
     * @param yPos   - y coordinates of image
     * @param width  - width of image
     * @param height - height of image
     */
    public void drawCannonImage(float xPos, float yPos, float width, float height) {
        parent.image(dCanon, xPos-30, yPos - (height / 2)-10, width, height);
    }

    public void drawCannonDisImage(float xPos, float yPos, float width, float height) {
        parent.image(dRed, xPos-30, yPos - (height / 2)-5, width-10, height-10);

    }

    /**
     * Draw a Bomber
     *
     * @param xPos   - x coordinates of image
     * @param yPos   - y coordinates of image
     * @param width  - width of image
     * @param height - height of image
     */
    public void drawBomberImage(float xPos, float yPos, float width, float height) {
        parent.image(dBomber, xPos, yPos - (height / 2), width, height);
    }

    /**
     * Draw information for the player. This includes:
     * - score
     * - number of missiles
     * - number of blackholes
     * - number of forcefields
     *
     * @param info - player information of the game
     */
    public void displayInfo(Inventory info, Level level) {
        drawText(16, "Level: " + level.levelNumber, 100, 25, 255);
        drawText(16, "Score: " + info.score, 100, 50, 255);
        drawText(16, "Missiles: " + info.missilesLeft, 100, 75, 255);
        drawText(16, "Blackholes: " + info.blackholesLeft, 675, 40, 255);
        drawText(16, "Forcefields: " + info.forcefieldsLeft,675, 65, 255);

    }


}