package objects.buildings;

import game.IDrawable;
import processing.core.PVector;

public abstract class Building implements IDrawable {

	public PVector position;
	public boolean destroyed;
	
	public abstract void destroy();
}
