package game.player.pacman;

import game.core.G;
import game.core.Game;
import gui.AbstractPlayer;

public final class RandomNonRevPacMan extends AbstractPlayer{	
	@Override
	public int getAction(Game game,long timeDue){			
		int[] directions=game.getPossiblePacManDirs(false);		//set flag as false to prevent reversals	
		return directions[G.rnd.nextInt(directions.length)];		
	}

	@Override
	public String getGroupName() {
		return "Random Non Reverse PacMan";
	}
}