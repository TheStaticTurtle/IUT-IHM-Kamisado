import java.awt.*;

class CustomColors {
	static Color GRAY    = new Color(128,128,128);
	static Color GREEN   = new Color(118,255,3);
	static Color RED     = new Color(255,61,0);
	static Color YELLOW  = new Color(255,234,0);
	static Color PINK    = new Color(239,154,154);
	static Color MAGENTA = new Color(213,0,249);
	static Color BLUE    = new Color(100,181,246);
	static Color ORANGE  = new Color(251,140,0);
}

public class Kamisado {
	static String NAME = "Kamisado";
	static String RULES = "Regles du kamisado\n\nLa règle de base est astucieuse et très simple:\n\t- Le joueur actif doit jouer la pièce dont la couleur correspond à celle de la case sur laquelle s'est arrêté son adversaire.\n\t- Une pièce doit toujours se déplacer vers l'avant (en ligne ou en diagonale).";
	static int TILE_SIZE = 100;
	static Color[] line1 = {CustomColors.GRAY,	CustomColors.GREEN,	CustomColors.RED,	CustomColors.YELLOW,	CustomColors.PINK,	CustomColors.MAGENTA,	CustomColors.BLUE,	CustomColors.ORANGE};
	static Color[] line2 = {CustomColors.MAGENTA,	CustomColors.GRAY,	CustomColors.YELLOW,	CustomColors.BLUE,	CustomColors.GREEN,	CustomColors.PINK,	CustomColors.ORANGE,	CustomColors.RED};
	static Color[] line3 = {CustomColors.BLUE,	CustomColors.YELLOW,	CustomColors.GRAY,	CustomColors.MAGENTA,	CustomColors.RED,	CustomColors.ORANGE,	CustomColors.PINK,	CustomColors.GREEN};
	static Color[] line4 = {CustomColors.YELLOW,	CustomColors.RED,	CustomColors.GREEN,	CustomColors.GRAY,	CustomColors.ORANGE,	CustomColors.BLUE,	CustomColors.MAGENTA,	CustomColors.PINK};
	static Color[] line5 = {CustomColors.PINK,	CustomColors.MAGENTA,	CustomColors.BLUE,	CustomColors.ORANGE,	CustomColors.GRAY,	CustomColors.GREEN,	CustomColors.RED,	CustomColors.YELLOW};
	static Color[] line6 = {CustomColors.GREEN,	CustomColors.PINK,	CustomColors.ORANGE,	CustomColors.RED,	CustomColors.MAGENTA,	CustomColors.GRAY,	CustomColors.YELLOW,	CustomColors.BLUE};
	static Color[] line7 = {CustomColors.RED,	CustomColors.ORANGE,	CustomColors.PINK,	CustomColors.GREEN,	CustomColors.BLUE,	CustomColors.YELLOW,	CustomColors.GRAY,	CustomColors.MAGENTA};
	static Color[] line8 = {CustomColors.ORANGE,	CustomColors.BLUE,	CustomColors.MAGENTA,	CustomColors.PINK,	CustomColors.YELLOW,	CustomColors.RED,	CustomColors.GREEN,	CustomColors.GRAY};
	static Color[][] lines = {line1,line2,line3,line4,line5,line6,line7,line8};

	public static void main(String[] args) {
		PlayerModel player1 = new PlayerModel(0);
		PlayerModel player2 = new PlayerModel(7);
		GameModel gameModel = new GameModel();
		Controller controller = new Controller(player1, player2,gameModel);
	}
}
