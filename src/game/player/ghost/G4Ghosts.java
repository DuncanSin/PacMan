package game.player.ghost;

import game.core.Game;
import gui.AbstractGhost;

public class G4Ghosts extends AbstractGhost {
	
	@Override
	public int[] getActions(Game game, long timeDue) {
		int[] directions = new int[Game.NUM_GHOSTS];
		int[] curPos = new int[Game.NUM_GHOSTS];
		int[] curDir = new int[Game.NUM_GHOSTS];
		int[] posDir = new int[4];
		
		int farthest = getFarthestPoint(game);

		double dist, temp;

		for (int i = 0; i < Game.NUM_GHOSTS; i++) {
			if (game.ghostRequiresAction(i)) {
				
				System.out.println(i);
				
				curPos[i] = game.getCurGhostLoc(i);
				curDir[i] = game.getCurGhostDir(i);

				if (game.isEdible(i) && game.getEdibleTime(i) > 20) {

					directions[i] = game.getGhostPath(i, farthest)[0];

				} else {
					posDir = game.getPossibleGhostDirs(i);

					dist = 10000;

					for (int j = 0; j < posDir.length; j++) {

						if (GhostNear(game, i, curPos)) {

							temp = game.getManhattenDistance(
									game.getNeighbour(curPos[i], posDir[j]),
									game.getCurPacManLoc());

						} else {
							temp = game.getPathDistance(
									game.getNeighbour(curPos[i], posDir[j]),
									game.getCurPacManLoc());
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

	private boolean GhostNear(Game game, int whichGhost, int[] others) {
		
		int ghosts = game.NUM_GHOSTS;
		
		for (int i = 0; i < ghosts; i++) {

			if (i == whichGhost) {
				continue;
			} else if (game.getPathDistance(game.getCurGhostLoc(whichGhost),
					game.getCurGhostLoc(i)) < 6) {
				return true;
			}

		}
		return false;
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
		return "Gruppe 4: Ghosts";
	}

}
