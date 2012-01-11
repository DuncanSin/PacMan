package game.player.pacman;

import game.core.G;
import game.core.Game;
import gui.AbstractPlayer;

public final class RandomPacMan extends AbstractPlayer{
	
	private long lastAction = 0;
	@Override
	public int getAction(Game game,long timeDue){
//		if(timeDue - lastAction > 1500){ // Change evry 1500 ms Randomly the direction 
//			int[] directions=game.getPossiblePacManDirs(true);		//set flag as true to include reversals		
//			this.lastAction = timeDue;
//			return directions[G.rnd.nextInt(directions.length)];
//		}
		if(Math.random() > 0.85){ // Change evry 1500 ms Randomly the direction 
			int[] directions=game.getPossiblePacManDirs(true);		//set flag as true to include reversals		
			this.lastAction = timeDue;
			return directions[G.rnd.nextInt(directions.length)];
		}
		return -1; // Neutral
	}

	@Override
	public String getGroupName() {
		return "Random PacMan";
	}
}