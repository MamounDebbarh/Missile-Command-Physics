package game;

import game.states.Game;
import game.states.Input;
import game.states.State;
import game.states.Start;
import objects.buildings.Cannon;
import processing.core.PApplet;

/**
 * Keep's track of the current State of the game and interacts with the main MissileCommand class used by Processing.
 */
public class Controller extends PApplet {
	
	public PApplet parent;



    public DrawEngine drawEngine;
    
    public Game context;
    private State state;

    
    Controller(PApplet parent, DrawEngine drawEngine) {
    	this.parent = parent;
    	this.context = new Game();
    	this.state = new Start(context, drawEngine);

	    this.drawEngine = drawEngine;
    }
    

    /**
     * Each step of the game.
     * Called at every frame.
     */
	void step() {
    	state.display();
    	state = state.update();

			float mouseX = parent.mouseX;
			float mouseY = parent.mouseY;
			Cannon cannon = context.getClosestCannon((int) mouseX, (int) mouseY);
			if (cannon != null && !cannon.destroyed) parent.line(mouseX, mouseY, cannon.position.x, cannon.position.y);


    }
    
	void handleInput(int mouseX, int mouseY, int mouseButton, int keyPressed) {
		Input input = new Input(mouseX, mouseY, mouseButton, keyPressed);
		state = state.handleInput(input);
	}
    


}