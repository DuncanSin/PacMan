package game.player.ghost;

import game.core.G;
import game.core.Game;
import gui.AbstractGhost;

public final class RandomGhosts extends AbstractGhost
{	
	@Override
	public int[] getActions(Game game,long timeDue)
	{	
		int[] directions=new int[Game.NUM_GHOSTS];
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for(int i=0;i<directions.length;i++)
			if(game.ghostRequiresAction(i))
			{			
				int[] possibleDirs=game.getPossibleGhostDirs(i);			
				directions[i]=possibleDirs[G.rnd.nextInt(possibleDirs.length)];
			}
		
		return directions;
	}

	@Override
	public String getGhostGroupName() {
		return "Random Ghost - Gruppe 1";
	}
}