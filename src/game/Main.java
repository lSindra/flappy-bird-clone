package game;

import game.content.entities.Pipes;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();

		// Initialize game objects
		Pipes pipes = new Pipes();
		game.addUpdatable(pipes);
		game.addRenderable(pipes);

		// Add updatables and renderables

		// Start!
		game.start();

	}
}
