package game.states;

/**
 * Class to abstract over all player input.
 */
public class Input {

	float mouseX, mouseY;
	int mouseButton, keyPressed;

	/**
	 * Input Method
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 * @param keyPressed
	 */
	public Input(float mouseX, float mouseY, int mouseButton, int keyPressed) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.keyPressed = keyPressed;
	}

}
