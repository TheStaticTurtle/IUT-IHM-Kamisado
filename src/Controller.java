public class Controller {
	PlayerModel player1 = null;
	PlayerModel player2 = null;
	GameModel gameModel;
	Vue game = null;
	ControllerButton buttonController = null;
	ControllerMenu menuController = null;

	public Controller(PlayerModel player1, PlayerModel player2,GameModel gameModel) {
		this.player1 = player1;
		this.player2 = player2;
		this.gameModel = gameModel;

		this.game = new Vue(player1, player2, gameModel);
		this.buttonController = new ControllerButton(this.player1,this.player2,this.gameModel,this.game);
		this.menuController = new ControllerMenu(this.player1,this.player2,this.gameModel,this.game);
		this.game.display();
		this.game.update();
	}
}
