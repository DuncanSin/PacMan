package game.player.ghost;

import game.core.Game;
import gui.AbstractGhost;

public class G4GhostsTest extends AbstractGhost {
	
	@Override
	public int[] getActions(Game game, long timeDue) {
		int[] directions = new int[Game.NUM_GHOSTS];
		int[] curPos = new int[Game.NUM_GHOSTS];
		int[] curDir = new int[Game.NUM_GHOSTS];
		int[] posDir = new int[4];
		
		int farthest = getFarthestPoint(game);

		double dist, temp, manhattenDistance, pathDistance;
		int nextGhost;

		for (int i = 0; i < Game.NUM_GHOSTS; i++) {
			if (game.ghostRequiresAction(i)) {
				
				curPos[i] = game.getCurGhostLoc(i);
				curDir[i] = game.getCurGhostDir(i);

				if (game.isEdible(i) && game.getEdibleTime(i) > 20) { // TODO: Abstand zu PacMan auf Geschwindigkeit umrechnen und optimieren

					directions[i] = game.getGhostPath(i, farthest)[0];

				} else {
					posDir = game.getPossibleGhostDirs(i);

					dist = Double.MAX_VALUE;
					temp = Double.MAX_VALUE;

					for (int j = 0; j < posDir.length; j++) {
						
						pathDistance = game.getPathDistance(
								game.getNeighbour(curPos[i], posDir[j]),
								game.getCurPacManLoc());
						
						manhattenDistance = game.getManhattenDistance(
								game.getNeighbour(curPos[i], posDir[j]),
								game.getCurPacManLoc());
						
						nextGhost = GhostNear(game, i);

						if (nextGhost < game.NUM_GHOSTS) {
							
							double nextGhostDistance = game.getPathDistance(
									game.getNeighbour(game.getCurGhostLoc(nextGhost), posDir[j]),
									game.getCurPacManLoc());
							
							boolean first = pathDistance < nextGhostDistance;
							
							if (first) {
								temp = pathDistance;
							} else {
								temp = manhattenDistance;
							}
						} else {
							temp = pathDistance;
						}

						if (temp <= dist) {
							dist = temp;
							directions[i] = posDir[j];
						}
					}
				}
			}
		}

		return directions;
	}

	private int GhostNear(Game game, int whichGhost) {
		
		int ghosts = game.NUM_GHOSTS;
		
		for (int i = 0; i < ghosts; i++) {

			if (i == whichGhost) {
				continue;
			} else if (game.getPathDistance(game.getCurGhostLoc(whichGhost),
					game.getCurGhostLoc(i)) < 6) {
				return i;
			}

		}
		return ghosts;
	}

	private int getFarthestPoint(Game game) {
		int dist, temp, loc;

		dist = 0;
		loc = 0;
		for (int i = 0; i <= 1291; i++) {
			temp = game.getPathDistance(game.getCurPacManLoc(), i);

			if (temp > dist) {
				dist = temp;
				loc = i;
			}
		}
		return loc;

	}

	@Override
	public String getGhostGroupName() {
		return "Gruppe4_Test_Ghosts";
	}

}