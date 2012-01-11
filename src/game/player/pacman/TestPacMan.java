package game.player.pacman;

import game.core.G;
import game.core.Game;
import game.core.Game.DM;
import gui.AbstractPlayer;

public class TestPacMan extends AbstractPlayer{

	private long lastDecision = 0;
	private boolean flee = false;
	
	@Override
	public int getAction(Game game, long timeDue) {
		
		if(timeDue - lastDecision > 2000){
			flee = (Math.random() < 0.3);
			lastDecision = timeDue;
		}
		if(flee){ // Weglaufen
			int nextGhost = 0;
			int distanceToGhost = Integer.MAX_VALUE;
			for(int i=0; i< Game.NUM_GHOSTS;i++){
				if(game.getPathDistance(game.getCurPacManLoc(),game.getCurGhostLoc(i)) < distanceToGhost){
					distanceToGhost = game.getPathDistance(game.getCurPacManLoc(),game.getCurGhostLoc(i));
					nextGhost = i;
				}
			}
			int[] neighbours = game.getPacManNeighbours();
			int newDistance = -1;
			int dir = 4; // neutral
			for(int i=0; i<neighbours.length;i++){
				if(neighbours[i]<0){ // Falls keine Zelle in dieser Nachbarschaft ist
					continue;
				}
				int tmpDistance = game.getPathDistance(neighbours[i], game.getCurGhostLoc(nextGhost));
				if(tmpDistance > newDistance){
					dir = i;
				}
			}
			return dir;
		}else{ // Pills sammeln 
			int current=game.getCurPacManLoc();
			//alle aktiven pills
			int[] activePills=game.getPillIndicesActive();
			//get all aktiven power pills
			int[] activePowerPills=game.getPowerPillIndicesActive();
			//alle möglichen Ziele (pill + powerPills) in ziele kopieren
			int[] targetsArray=new int[activePills.length+activePowerPills.length];
			for(int i=0;i<activePills.length;i++){
				targetsArray[i]=activePills[i];
			}
			
			for(int i=0;i<activePowerPills.length;i++){
				targetsArray[activePills.length+i]=activePowerPills[i];	
			}
			// laeuft zu den neachsten zielen
			return game.getNextPacManDir(game.getTarget(current,targetsArray,true,G.DM.PATH),true,Game.DM.PATH);
		}
		
	}

	@Override
	public String getGroupName() {
		return "Unentschlossener Pac Man";
	}

}
