import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMenu implements ActionListener {
	PlayerModel player1;
	PlayerModel player2;
	GameModel gameModel;
	Vue gameView;

	public ControllerMenu(PlayerModel player1, PlayerModel player2, GameModel gameModel, Vue gameView) {
		this.player1 = player1;
		this.player2 = player2;
		this.gameView = gameView;
		this.gameModel = gameModel;

		this.gameView.setMenuController(this);
	}

	public void actionPerformed(final ActionEvent e) {
		JOptionPane d =  new JOptionPane();
		//switch (e.getID() == f.itemInterface1.getId)
		System.out.print("Menu action: ");
		if(e.getActionCommand().equals(this.gameView.itemReplay.getText())) {
			System.out.println("replay 1v1");
			reset1V1();
		} else if(e.getActionCommand().equals(this.gameView.itemReplayAI.getText())) {
			System.out.println("replay 1vOrdi");
		} else if(e.getActionCommand().equals(this.gameView.itemRules.getText())) {
			System.out.println("Rules");
			d.showMessageDialog(this.gameView, Kamisado.RULES, "Regles", JOptionPane.INFORMATION_MESSAGE);
		} else {
			System.out.println("Unkown");
		}
	}

	public void reset1V1() {
		this.player1.reset();
		this.player2.reset();
		this.gameModel.reset();

		this.gameView.update_after_reset();
	}

}
