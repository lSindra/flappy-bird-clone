package game.content.entities;

import game.Input;
import game.content.Renderable;
import game.content.Updatable;
import java.awt.Graphics2D;

public class Bird implements Updatable, Renderable {

	private float x, y;
	private float yVelocity;

	private static final float GRAVITY = 0.25f;
	private static final float BASE_Y_VELOCITY = -6.0f;

	public void resetBird() {
		x = 100;
		y = 100;
		yVelocity = BASE_Y_VELOCITY;
	}

	private void flap() {
		yVelocity = BASE_Y_VELOCITY;
	}

	@Override
	public void update(Input input) {

	}
	
	@Override
	public void render(Graphics2D graphic, float interpolation) {

	}
}
