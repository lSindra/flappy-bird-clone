package game.content.entities;

import game.Input;
import game.content.Renderable;
import game.content.Updatable;
import game.engine.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bird implements Updatable, Renderable {

	private float xPosition, yPosition;
	private float yVelocity;
	private Pipes pipes;
	private int scoredPipe = 0;
	private int score = 0;
	private Font gameFont = new Font("Arial", Font.BOLD, 30);
	private BufferedImage flapUp;
	private BufferedImage flapDown;

	private static final float GRAVITY = 0.25f;
	private static final float BASE_Y_VELOCITY = -6.0f;

	public Bird(Pipes pipes) {
		resetBird();

		this.pipes = pipes;

		try {
			flapUp = Sprite.getSprite("bird_up.png");
			flapDown = Sprite.getSprite("bird_down.png");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public void resetBird() {
		xPosition = 100;
		yPosition = 100;
		yVelocity = BASE_Y_VELOCITY;
	}

	private void flap() {
		yVelocity = BASE_Y_VELOCITY;
	}

	@Override
	public void update(Input input) {
		yPosition += yVelocity;
		yVelocity += GRAVITY;

		if(yPosition < 0) {
			yPosition = 0;
			yVelocity = 0;
		}

		if (input.isSpacePressed()) {
			flap();
		}
	}
	
	@Override
	public void render(Graphics2D graphic, float interpolation) {

	}
}
