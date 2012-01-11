package game.player.ghost;

import game.core.Game;
import gui.AbstractGhost;

public class G4Ghosts extends AbstractGhost {

	@Override
	public int[] getActions(Game game, long timeDue) {
		return null;
	}

	@Override
	public String getGhostGroupName() {
		return "Gruppe 4: Ghosts";
	}

}
