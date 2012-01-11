package game.player.ghost;

import game.core.Game;
import gui.AbstractGhost;

public class G4Ghosts extends AbstractGhost {

	@Override
	public int[] getActions(Game game, long timeDue) {
		int[] directions=new int[Game.NUM_GHOSTS];
		int[] curPos=new int[Game.NUM_GHOSTS];
		int[] curDir=new int[Game.NUM_GHOSTS];
		int[] posDir=new int[4];
		
		double dist, temp;
		
		for(int i = 0; i<Game.NUM_GHOSTS; i++){
			curPos[i] = game.getCurGhostLoc(i);
			curDir[i] = game.getCurGhostDir(i);
			
			posDir = game.getGhostNeighbours(i);
			
			dist = 0;
			
			for(int j=0; j<4; j++){
				temp = game.getEuclideanDistance(posDir[j], game.getCurPacManLoc());
				
				if(temp < dist){
					dist = temp;
					directions[i] = posDir[j];
				}
			}
		}
		
		
		
		
		return directions;
	}

	@Override
	public String getGhostGroupName() {
		return "Gruppe 4: Ghosts";
	}

}
