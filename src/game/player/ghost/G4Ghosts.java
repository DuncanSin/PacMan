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
				curPos[i] = game.getCurGhostLoc(i);
				curDir[i] = game.getCurGhostDir(i);

				if (game.isEdible(i) && game.getEdibleTime(i) > 20) {

					directions[i] = game.getGhostPath(i, farthest)[0];

				} else {
					posDir = game.getPossibleGhostDirs(i);

					dist = 10000;

					for (int j = 0; j < posDir.length; j++) {
						temp = game.getPathDistance(
								game.getNeighbour(curPos[i], posDir[j]),
								game.getCurPacManLoc());

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
