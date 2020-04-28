enum GameState {
	PLAYING_PLAYER1_SELECTING,
	PLAYING_PLAYER1_PLAYING,
	PLAYING_PLAYER2_SELECTING,
	PLAYING_PLAYER2_PLAYING,
	ENDED
}


public class GameModel {
	GameState state;
	int moves;
	Tower lastMove;

	public GameModel() {
		state = GameState.PLAYING_PLAYER1_SELECTING;
		moves=0;
	}

	public void reset() {
		state = GameState.PLAYING_PLAYER1_SELECTING;
		moves=0;
	}
}
