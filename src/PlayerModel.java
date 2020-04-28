import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

class Tower {
	int x = 0;
	int y = 0;
	boolean selected = false;
	Color color;

	public Tower(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = Kamisado.lines[y][x];
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tower tower = (Tower) o;
		return x == tower.x &&
				y == tower.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}

public class PlayerModel {
	ArrayList<Tower> towers = new ArrayList<Tower>();
	int y;

	public PlayerModel(int y) {
		this.y = y;
		for(int x=0; x<8; x++) {
			this.towers.add(new Tower(x,y));
		}
	}

	public void reset() {
		this.towers.clear();
		for(int x=0; x<8; x++) {
			this.towers.add(new Tower(x,y));
		}
	}

	public Tower hasTowerIn(int x, int y) {
		return this.towers.stream().filter(f -> f.equals(new Tower(x,y))).findFirst().orElse(null);
	}

	public Tower getSelected() {
		return towers.stream().filter(f -> f.selected).findFirst().orElse(null);
	}
}
