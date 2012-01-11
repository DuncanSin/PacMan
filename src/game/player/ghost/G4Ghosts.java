package game.player.ghost;

import game.core.Game;
import gui.AbstractGhost;

public class G4Ghosts extends AbstractGhost {

	@Override
	public int[] getActions(Game game, long timeDue) {
		int[] directions=new int[Game.NUM_GHOSTS];
		int[] curPos=new int[Game.NUM_GHOSTS];
		int[] curDir=new int[Game.NUM_GHOSTS];
		
		for(int i = 0; i<Game.NUM_GHOSTS; i++){
			curPos[i] = game.getCurGhostLoc(i);
			curDir[i] = game.getCurGhostDir(i);
		}
		
		
		
		
		return directions;
	}

	@Override
	public String getGhostGroupName() {
		return "Gruppe 4: Ghosts";
	}

}
