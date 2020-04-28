import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerButton implements ActionListener {

	PlayerModel player1;
	PlayerModel player2;
	GameModel gameModel;
	Vue gameView;

	public ControllerButton(PlayerModel player1, PlayerModel player2, GameModel gameModel, Vue gameView) {
		this.player1 = player1;
		this.player2 = player2;
		this.gameView = gameView;
		this.gameModel = gameModel;

		this.gameView.setButtonController(this);
	}

	void checkIfGameEnded() {
		for(Tower t : player1.towers) {
			if(t.y == 7) {
				gameModel.state = GameState.ENDED;
				JOptionPane.showMessageDialog(null,
						"Le joueur n°1 a gagner la partie",
						"Partie finie.",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		for(Tower t : player2.towers) {
			if(t.y == 0) {
				gameModel.state = GameState.ENDED;
				JOptionPane.showMessageDialog(null,
						"Le joueur n°2 a gagner la partie",
						"Partie finie.",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] command = e.getActionCommand().split(":");
		if(command[0].equals("press")) {

			if(gameModel.state == GameState.PLAYING_PLAYER1_SELECTING) {
				for (Tower t : player1.towers) {
					if(t.x == Integer.parseInt(command[1]) && t.y == Integer.parseInt(command[2])) {
						t.selected = true;
						gameModel.state = GameState.PLAYING_PLAYER1_PLAYING;
						gameModel.moves++;
					}
				}
				checkIfGameEnded();
				this.gameView.update();
				return;
			}
			if(gameModel.state == GameState.PLAYING_PLAYER1_PLAYING) {
				Tower t = player1.getSelected();
				int old_x = t.x;
				int old_y = t.y;
				t.x = Integer.parseInt(command[1]);
				t.y = Integer.parseInt(command[2]);
				this.gameView.board.tiles[old_x][old_y].recalculateIcon();
				this.gameView.board.tiles[t.x][t.y].recalculateIcon();
				t.selected = false;

				for(Tower t2 : player2.towers){
					if(t2.color == gameView.board.tiles[t.x][t.y].getBackground()) {
						t2.selected = true;
					}
				}

				gameModel.state = GameState.PLAYING_PLAYER2_PLAYING;
				gameModel.moves++;

				checkIfGameEnded();
				this.gameView.update();
				return;
			}

			/*if(gameModel.state == GameState.PLAYING_PLAYER2_SELECTING) {
				System.out.print("PLAYING_PLAYER2_SELECTING -> ");
				for (Tower t : player2.towers) {
					if(t.x == Integer.parseInt(command[1]) && t.y == Integer.parseInt(command[2])) {
						t.selected = true;
						gameModel.state = GameState.PLAYING_PLAYER2_PLAYING;
						gameModel.moves++;
					}
				}
				this.gameView.update();
				return;
			}*/

			if(gameModel.state == GameState.PLAYING_PLAYER2_PLAYING) {
				Tower t = player2.getSelected();
				int old_x = t.x;
				int old_y = t.y;
				t.x = Integer.parseInt(command[1]);
				t.y = Integer.parseInt(command[2]);
				this.gameView.board.tiles[old_x][old_y].recalculateIcon();
				this.gameView.board.tiles[t.x][t.y].recalculateIcon();
				t.selected = false;

				for(Tower t2 : player1.towers){
					if(t2.color == gameView.board.tiles[t.x][t.y].getBackground()) {
						t2.selected = true;
					}
				}
				gameModel.state = GameState.PLAYING_PLAYER1_PLAYING;
				gameModel.moves++;
				checkIfGameEnded();
				this.gameView.update();
				return;
			}
		}
	}
}
