package game;

import game.content.entities.Bird;
import game.content.entities.Pipes;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();

		Pipes pipes = new Pipes();
        Bird bird = new Bird(pipes);

        setGameObject(game, pipes);
        setGameObject(game, bird);

		game.start();

	}

    private static void setGameObject(Game game, Bird bird) {
        game.addUpdatable(bird);
        game.addRenderable(bird);
    }

    private static void setGameObject(Game game, Pipes pipes) {
        game.addUpdatable(pipes);
        game.addRenderable(pipes);
    }
}
