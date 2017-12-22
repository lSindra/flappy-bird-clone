package game;

import java.awt.Canvas;
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

	private long nextGameTick;
	private int ticks;
	private long timeAtLastFPSCheck;
	private boolean gameRunning;
	final int TICKS_PER_SECOND = 60;
	final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIPS = 5;

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

	public void start() {
		// Init window
		initWindow();

		// Init input
		input = new Input();

		// Init game loop
		nextGameTick = getCurrentTime();
		int loops;
		float interpolation;

		timeAtLastFPSCheck = 0;
		ticks = 0;

		gameRunning = true;

		gameLoop();
	}

	private void gameLoop() {
		while (gameRunning) {
			update();

			render();

			checkFPS();
		}
	}

	private void checkFPS() {
		if (getCurrentTime() - timeAtLastFPSCheck >= 1000) {
			System.out.println("FPS: " + ticks);
			gameWindow.setTitle(gameName + " - FPS: " + ticks);
			ticks = 0;
			timeAtLastFPSCheck = getCurrentTime();
		}
	}

	private void render() {
		float interpolation;
		interpolation =
			(float) (getCurrentTime() + TIME_PER_TICK - nextGameTick) / (float) TIME_PER_TICK;
		renderObjects(interpolation);
	}

	private void update() {
		int loops;
		loops = 0;

		while (nextGameTick < getCurrentTime() && loops < MAX_FRAMESKIPS) {
			updateObjects();

			ticks++;
			loops++;
			nextGameTick += TIME_PER_TICK;
		}
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
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
		BufferStrategy bufferStrategy = getOrCreateBufferStrategy();

		Graphics2D graphics = getGraphics(bufferStrategy);

		for (Renderable renderable : renderablesObjects) {
			renderable.render(graphics, interpolation);
		}

		graphics.dispose();
		bufferStrategy.show();
	}

	private Graphics2D getGraphics(BufferStrategy bufferStrategy) {
		Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		graphics.clearRect(0, 0, game.getWidth(), game.getHeight());
		return graphics;
	}

	private BufferStrategy getOrCreateBufferStrategy() {
		BufferStrategy bufferStrategy = game.getBufferStrategy();
		if (bufferStrategy == null) {
			game.createBufferStrategy(2);
		}
		return bufferStrategy;
	}
}
