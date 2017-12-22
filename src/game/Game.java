package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game {

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;

	private String gameName = "Flappy Bird";
	private Canvas game = new Canvas();
	private JFrame gameWindow;

	private Input input;

	private ArrayList<Updatable> updatableObjects = new ArrayList<>();
	private ArrayList<Renderable> renderablesObjects = new ArrayList<>();

	public void addUpdatable(Updatable updatable) {
		updatableObjects.add(updatable);
	}

	public void removeUpdatable(Updatable updatable) {
		updatableObjects.remove(updatable);
	}

	public void addRenderable(Renderable renderable) {
		renderablesObjects.remove(renderable);
	}

	public void removeRenderable(Renderable renderable) {
		renderablesObjects.remove(renderable);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.renderablesObjects.add(new Renderable() {
			@Override
			public void render(Graphics2D graphics, float interpolation) {
				graphics.setColor(Color.RED);
				graphics.drawRect(300, 250, 50, 100);
			}
		});
		game.start();
	}

	private void start() {
		// Init window
		initWindow();

		// Init input
		input = new Input();

		// Init game loop
		final int TICKS_PER_SECOND = 60;
		final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIPS = 5;

		long nextGameTick = System.currentTimeMillis();
		int loops;
		float interpolation;

		long timeAtLastFPSCheck = 0;
		int ticks = 0;

		boolean running = true;

		while (running) {
			// Updating
			loops = 0;

			while (nextGameTick < System.currentTimeMillis() && loops < MAX_FRAMESKIPS) {
				updateObjects();

				ticks++;
				loops++;
				nextGameTick += TIME_PER_TICK;
			}

			// Rendering
			interpolation = (float) (System.currentTimeMillis() + TIME_PER_TICK - nextGameTick) /
				(float) TIME_PER_TICK;
			renderObjects(interpolation);

			// FPS Check
			if (System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
				System.out.println("FPS: " + ticks);
				gameWindow.setTitle(gameName + " - FPS: " + ticks);
				ticks = 0;
				timeAtLastFPSCheck = System.currentTimeMillis();
			}
		}

		// Game end
	}

	private void initWindow() {
		Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT);
		gameWindow = new JFrame(gameName);

		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setSize(gameSize);
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);

		game.setSize(gameSize);
		game.setMinimumSize(gameSize);
		game.setMaximumSize(gameSize);
		game.setPreferredSize(gameSize);

		gameWindow.add(game);
		gameWindow.setLocationRelativeTo(null);
	}

	private void updateObjects() {
		for (Updatable updatable : updatableObjects) {
			updatable.update(input);
		}
	}

	private void renderObjects(float interpolation) {
		BufferStrategy bufferStrategy = game.getBufferStrategy();
		if (bufferStrategy == null) {
			game.createBufferStrategy(2);
			return;
		}

		Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		graphics.clearRect(0, 0, game.getWidth(), game.getHeight());

		for (Renderable renderable : renderablesObjects) {
			renderable.render(graphics, interpolation);
		}

		graphics.dispose();
		bufferStrategy.show();
	}
}
