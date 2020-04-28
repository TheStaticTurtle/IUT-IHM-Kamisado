import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class TowerIcon implements Icon {
	int height;
	int width;
	Color bg;
	Color fg;
	public TowerIcon(int width, int height, Color bg, Color fg) {
		this.height = height;
		this.width = width;
		this.fg= fg;
		this.bg= bg;
	}

	public int getIconWidth() {
		return this.width;
	}

	public int getIconHeight() {
		return this.height;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		int radius = (int)(getIconWidth()/2.7);
		x = getIconWidth() /2;
		y = getIconHeight()/2;

		g2.setColor(this.bg);
		g2.fillOval(x-radius, y-radius, 2*radius, 2*radius);

		int a_len = 10;
		g2.setStroke(new BasicStroke(12));
		g2.setColor(Color.darkGray);
		g2.drawArc(x-radius+5, y-radius+5, 2*radius-10, 2*radius-10 ,0,360);
		g2.setColor(Color.gray);
		for(int a=0; a<=360; a+=360/a_len*2) {
			g2.drawArc(x-radius+5, y-radius+5, 2*radius-10, 2*radius-10 ,a,a_len);
		}

		int s = (int)(getIconHeight() /2.5);
		g2.setColor(this.fg);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, s));
		g2.drawString("\uD83D\uDC09",x-s/2,(int)(y+s/2.7));
	}

	public BufferedImage getImage() {
		int w = this.getIconWidth();
		int h = this.getIconHeight();
		BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

		Graphics c = image.createGraphics();
		this.paintIcon(null, c, 0, 0);
		c.dispose();

		int radius = (int)(getIconWidth()/3);
		int deltaX = (getIconWidth()-radius*2)/2;
		int deltaY = (getIconWidth()-radius*2)/2;
		return image.getSubimage(deltaX,deltaY,w-deltaX*2,h-deltaY*2);
	}
}

class TileView extends JButton {
	Vue vue;
	int x;
	int y;
	TileView(Color color, Vue vue, int x, int y) {
		setPreferredSize(new Dimension(Kamisado.TILE_SIZE,Kamisado.TILE_SIZE));
		setOpaque(true);
		setBackground(color);
		setBorder(null);
		this.vue = vue;
		this.x = x;
		this.y = y;
		recalculateIcon();
	}

	void recalculateIcon() {
		Tower tp1 = this.vue.player1.hasTowerIn(this.x,this.y);
		Tower tp2 = this.vue.player2.hasTowerIn(this.x,this.y);

		if(tp1 !=null) {
			setIcon(new TowerIcon(Kamisado.TILE_SIZE,Kamisado.TILE_SIZE,Color.white,tp1.color));
		} else if(tp2 !=null) {
			setIcon(new TowerIcon(Kamisado.TILE_SIZE,Kamisado.TILE_SIZE,Color.black,tp2.color));
		} else {
			setIcon(null);
		}

	}
}

class BoardPanel extends JPanel {
	TileView[][] tiles= new TileView[8][8];
	Vue vue;

	public BoardPanel(Vue vue) {
		this.vue = vue;
		setLayout(new GridLayout(Kamisado.line1.length, Kamisado.lines.length));

		int x=0;
		int y=0;
		for (Color[] line : Kamisado.lines) {
			for (Color color : line) {
				tiles[x][y] = new TileView(color,this.vue,x,y);
				tiles[x][y].setEnabled(false);
				tiles[x][y].setActionCommand("press:"+x+":"+y);
				add(tiles[x][y]);
				x++;
			}
			x=0;
			y++;
		}
	}
}

public class Vue extends JFrame {
	PlayerModel player1 = null;
	PlayerModel player2 = null;
	GameModel gameModel = null;
	ControllerMenu controlMenu;

	BoardPanel board;

	JMenuItem itemReplay;
	JMenuItem itemReplayAI;
	JMenuItem itemRules;

	JMenuBar barMenu;
	JMenu menu;

	public Vue(PlayerModel player1, PlayerModel player2, GameModel gameModel) {
		this.player1 = player1;
		this.player2 = player2;
		this.gameModel = gameModel;
		init();
	}

	public void setButtonController(ActionListener l) {
		for (TileView[] line : this.board.tiles) {
			for (TileView tile : line) {
				tile.addActionListener(l);
			}
		}
	}

	public void setMenuController(ControllerMenu ctrlr) {
		this.controlMenu = ctrlr;
		itemReplay.addActionListener(ctrlr);
		itemReplayAI.addActionListener(ctrlr);
		itemRules.addActionListener(ctrlr);
	}

	void init() {
		barMenu = new JMenuBar();
		menu = new JMenu("Options");

		itemReplay = new JMenuItem("Rejouer (Multijoueur)");
		itemReplayAI = new JMenuItem("Rejouer (Ordinateur)");
		itemRules = new JMenuItem("Regles");

		barMenu = new JMenuBar();

		menu.add(itemReplay);
		menu.add(itemReplayAI);
		menu.addSeparator();
		menu.add(itemRules);

		barMenu.add(menu);
		setJMenuBar(barMenu);

		board = new BoardPanel(this);

		menu = new JMenu("Jeu");
	}

	public void update() {
		Tower selectedTower;
		Border b = BorderFactory.createLineBorder(Color.black,5);

		for (int y=0; y<Kamisado.lines.length ;y++) {
			for (int x=0; x<Kamisado.line1.length ;x++) {
				this.board.tiles[x][y].setBorder(null);
				this.board.tiles[x][y].setEnabled(false);
				this.board.tiles[x][y].setText("");
			}
		}

		setIgnoreRepaint(true);

		switch (this.gameModel.state) {
			case PLAYING_PLAYER1_SELECTING:
				for(Tower tower: player1.towers) {
					this.board.tiles[tower.x][tower.y].setBorder(b);
					this.board.tiles[tower.x][tower.y].setEnabled(true);
				}
				break;

			case PLAYING_PLAYER1_PLAYING:
				selectedTower = this.player1.getSelected();
				if(selectedTower != null) {
					int x = selectedTower.x;
					for(int y=selectedTower.y; y<Kamisado.lines.length; y++) {
						if(player2.hasTowerIn(x,y) != null) break;
						if(player1.hasTowerIn(x,y) == null) {
							this.board.tiles[x][y].setBorder(b);
							this.board.tiles[x][y].setEnabled(true);
							this.board.tiles[x][y].setText("\uD83E\uDC57");
						} else if(!(selectedTower.x == x && selectedTower.y == y)) { break; }
						x--;
						if(x<0) break;
					}

					x = selectedTower.x;
					for(int y=selectedTower.y; y<Kamisado.lines.length; y++) {
						if(player2.hasTowerIn(x,y)!= null) break;
						if(player1.hasTowerIn(x,y) == null) {
							this.board.tiles[x][y].setBorder(b);
							this.board.tiles[x][y].setEnabled(true);
							this.board.tiles[x][y].setText("\uD83E\uDC56");
						} else if(!(selectedTower.x == x && selectedTower.y == y)) { break; }
						x++;
						if(x>7) break;
					}

					for(int y=selectedTower.y; y<Kamisado.lines.length; y++) {
						if(player2.hasTowerIn(selectedTower.x,y) != null) break;
						if(player1.hasTowerIn(selectedTower.x,y) == null) {
							this.board.tiles[selectedTower.x][y].setBorder(b);
							this.board.tiles[selectedTower.x][y].setEnabled(true);
							this.board.tiles[selectedTower.x][y].setText("↓");
						} else if(!(selectedTower.y == y)) { break; }
					}
				}
				break;

			case PLAYING_PLAYER2_SELECTING:
				for(Tower tower: player2.towers) {
					this.board.tiles[tower.x][tower.y].setBorder(b);
					this.board.tiles[tower.x][tower.y].setEnabled(true);
				}
				break;

			case PLAYING_PLAYER2_PLAYING:
				selectedTower = this.player2.getSelected();

				if(selectedTower != null) {
					int x = selectedTower.x;
					for(int y=selectedTower.y; y>=0; y--) {
						if(player1.hasTowerIn(x,y) != null) break;
						if(player2.hasTowerIn(x,y) == null) {
							this.board.tiles[x][y].setBorder(b);
							this.board.tiles[x][y].setEnabled(true);
							this.board.tiles[x][y].setText("\uD83E\uDC54");
						}else if(!(selectedTower.x == x && selectedTower.y == y)) { break; }
						x--;
						if(x<0) break;
					}

					x = selectedTower.x;
					for(int y=selectedTower.y; y>=0; y--) {
						if(player1.hasTowerIn(x,y)!= null) break;
						if(player2.hasTowerIn(x,y)== null) {
							this.board.tiles[x][y].setBorder(b);
							this.board.tiles[x][y].setEnabled(true);
							this.board.tiles[x][y].setText("\uD83E\uDC55");
						}else if(!(selectedTower.x == x && selectedTower.y == y)) { break; }
						x++;
						if(x>7) break;
					}

					for(int y=selectedTower.y; y>=0; y--) {
						if(player1.hasTowerIn(selectedTower.x,y)!= null) break;
						if(player2.hasTowerIn(selectedTower.x,y)== null) {
							this.board.tiles[selectedTower.x][y].setBorder(b);
							this.board.tiles[selectedTower.x][y].setEnabled(true);
							this.board.tiles[selectedTower.x][y].setText("↑");
						} else if(!(selectedTower.y == y)) { break; }
					}
				}
				break;

			case ENDED:
				break;
		}

		setIgnoreRepaint(false);
		repaint();
	}
	public void update_after_reset() {
		update();
		for (int y=0; y<Kamisado.lines.length ;y++) {
			for (int x=0; x<Kamisado.line1.length ;x++) {
				this.board.tiles[x][y].recalculateIcon();
			}
		}
	}

	public void display() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(board);

		pack();
		setTitle(Kamisado.NAME);
		setIconImage(new TowerIcon(512,512,Color.WHITE,Color.ORANGE).getImage());
		setVisible(true);
		setResizable(false);

	}
}
