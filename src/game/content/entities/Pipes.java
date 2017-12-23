package game.content.entities;

import game.Game;
import game.Input;
import game.content.Renderable;
import game.content.Updatable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Pipes implements Updatable, Renderable {

	private static final int PIPE_WIDTH = 100;
	private static final int PIPE_HORIZONTAL_SPACE = 210;
	private static final int PIPE_VERTICAL_SPACE = 180;
	private static final int MIN_PIPE_HEIGHT = Game.HEIGHT / 10;
	private static final float MAX_PIPE_HEIGHT = Game.HEIGHT * 0.4f;

	private static final int FIRST_PIPE = 0;
	private static final int SECOND_PIPE = 1;
	private static final int THIRD_PIPE = 2;

	private static final int X_COORD = 0;
	private static final int Y_COORD = 1;

	private float xVelocity = -5.0f;

	private int currentPipe;

	private float[][] pipeCoords = new float[3][2];

	private Random random;

	public void setPipes() {
		random = new Random();

		resetPipes();
	}

	public void resetPipes() {
		currentPipe = 0;

		float totalSpaceX = PIPE_WIDTH + PIPE_HORIZONTAL_SPACE;
		float firstPipeX = Game.WIDTH * 2;
		float secondPipeX = firstPipeX + totalSpaceX;
		float thirdPipeX = secondPipeX + totalSpaceX;

		pipeCoords[FIRST_PIPE][X_COORD] = firstPipeX;
		pipeCoords[SECOND_PIPE][X_COORD] = secondPipeX;
		pipeCoords[THIRD_PIPE][X_COORD] = thirdPipeX;

		for (float[] pipeCoord : pipeCoords) {
			pipeCoord[Y_COORD] = getRandomY();
		}
	}

	private int getRandomY() {
		return random.nextInt((int) MAX_PIPE_HEIGHT + MIN_PIPE_HEIGHT);
	}

	@Override
	public void update(Input input) {
		updatePipeCoords();

		setCurrentPipe();
	}

	private void updatePipeCoords() {
		for (float[] pipeCoord : pipeCoords) {
			pipeCoord[X_COORD] += xVelocity;
		}
	}

	private void setCurrentPipe() {
		float currentPipeX = pipeCoords[currentPipe][X_COORD];
		if (currentPipeX + PIPE_WIDTH < 0) {
			int lastPipe = (currentPipe + 2) % 3;
			float lastPipeX = pipeCoords[lastPipe][X_COORD];

			pipeCoords[currentPipe][X_COORD] = lastPipeX + PIPE_WIDTH + PIPE_HORIZONTAL_SPACE;
			pipeCoords[currentPipe][Y_COORD] = getRandomY();

			setLastPipe();
		}
	}

	public void setLastPipe() {
		currentPipe = (currentPipe + 1) % 3;
	}

	@Override
	public void render(Graphics2D graphic, float interpolation) {
		graphic.setColor(Color.RED);

		for (float[] pipeCoord : pipeCoords) {
			float pipeX = pipeCoord[X_COORD];
			int interpolatedPipeX = (int) (pipeX + (xVelocity * interpolation));

			int topPipeY = 0;
			int topPipeHeight = (int) pipeCoord[Y_COORD];

			int bottomPipeY = topPipeHeight + PIPE_VERTICAL_SPACE;
			int bottomPipeHeight = Game.HEIGHT;

			graphic.fillRect(interpolatedPipeX, topPipeY, PIPE_WIDTH, topPipeHeight);
			graphic.fillRect(interpolatedPipeX, bottomPipeY, PIPE_WIDTH, bottomPipeHeight);
		}
	}

	public float[] getCurrentPipe() {
		return pipeCoords[currentPipe];
	}

	public int getCurrentPipeID() {
		return currentPipe;
	}

	public int getPipeWidth() {
		return PIPE_WIDTH;
	}

	public int getPipeHorizontalSpace() {
		return PIPE_HORIZONTAL_SPACE;
	}

	public int getPipeVerticalSpace() {
		return PIPE_VERTICAL_SPACE;
	}
}
