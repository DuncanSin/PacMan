package game.player.ghost;

import game.core.G;
import game.core.Game;
import gui.AbstractGhost;

public final class AttractRepelGhosts extends AbstractGhost
{	
	private final static float CONSISTENCY=0.9f;	//move towards/away with this probability
	private boolean attract;
	
	public AttractRepelGhosts()		
	{
		this.attract= true;						//approach or retreat from Ms Pac-Man
	}
	
	@Override
	public int[] getActions(Game game,long timeDue){		
		int[] directions=new int[Game.NUM_GHOSTS];
		
		for(int i=0;i<directions.length;i++)		//for each ghost
			if(game.ghostRequiresAction(i))			//if it requires an action
			{
				if(Game.rnd.nextFloat()<CONSISTENCY)	//approach/retreat from the current node that Ms Pac-Man is at
					directions[i]=game.getNextGhostDir(i,game.getCurPacManLoc(),attract,Game.DM.PATH);
				else									//else take a random action
				{					
					int[] possibleDirs=game.getPossibleGhostDirs(i);	//takes a random LEGAL action. Could also just return any random number		
					directions[i]=possibleDirs[G.rnd.nextInt(possibleDirs.length)];
				}
			}

		return directions;
	}

	@Override
	public String getGhostGroupName() {
		return "Attract Repel Ghosts";
	}
}