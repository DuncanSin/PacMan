/*
 * Implementation of "Ms Pac-Man" for the "Ms Pac-Man versus Ghost Team Competition", brought
 * to you by Philipp Rohlfshagen, David Robles and Simon Lucas of the University of Essex.
 * 
 * www.pacman-vs-ghosts.net
 * 
 * Code written by Philipp Rohlfshagen, based on earlier implementations of the game by
 * Simon Lucas and David Robles. 
 * 
 * You may use and distribute this code freely for non-commercial purposes. This notice 
 * needs to be included in all distributions. Deviations from the original should be 
 * clearly documented. We welcome any comments and suggestions regarding the code.
 */
package game.core;

import java.util.Random;

/*
 * This interface defines the contract between the game engine and the controllers. It provides all
 * the methods a controller may use to (a) query the game state, (b) compute game-related attributes
 * and (c) test moves by using a forward model (i.e., copy() followed by advanceGame()).
 */
public interface Game
{
	//These constants specify the exact nature of the game
	public static final int UP=0;							//direction going up
	public static final int RIGHT=1;						//direction going right
	public static final int DOWN=2;							//direction going down
	public static final int LEFT=3;							//direction going left
	public static final int EMPTY=-1;						//value of an non-existing neighbour
	public static final int PILL=10;						//points for a pill
	public static final int POWER_PILL=50;					//points for a power pill
	public static final int GHOST_EAT_SCORE=200;			//score for the first ghost eaten (doubles every time for the duration of a single power pill)
	public static final int EDIBLE_TIME=200;				//initial time a ghost is edible for (decreases as level number increases) 
	public static final float EDIBLE_TIME_REDUCTION=0.9f;	//reduction factor by which edible time decreases as level number increases
	public static final int[] LAIR_TIMES={40,60,80,100};	//time spend in the lair by each ghost at the start of a level
	public static final int COMMON_LAIR_TIME=40;			//time spend in lair after being eaten
	public static final float LAIR_REDUCTION=0.9f;			//reduction factor by which lair times decrease as level number increases
	public static final int LEVEL_LIMIT=3000;				//time limit for a level
	public static final float GHOST_REVERSAL=0.0015f;		//probability of a global ghost reversal event
	public static final int MAX_LEVELS=16;					//maximum number of levels played before the end of the game
	public static final int EXTRA_LIFE_SCORE=10000;			//extra life is awarded when this many points have been collected
	public static final int EAT_DISTANCE=2;					//distance in the connected graph considered close enough for an eating event to take place
	public static final int NUM_GHOSTS=4;					//number of ghosts in the game
	public static final int NUM_MAZES=4;					//number of different mazes in the game
	public static final int DELAY=40;						//delay (in milliseconds) between game advancements						
	public static final int NUM_LIVES=3;					//total number of lives Ms Pac-Man has (current + NUM_LIVES-1 spares)
	public static final int INITIAL_PAC_DIR=3;				//initial direction taken by Ms Pac-Man
	public static final int[] INITIAL_GHOST_DIRS={3,1,3,1};	//initial directions for the ghosts (after leaving the lair)
	public static final int GHOST_SPEED_REDUCTION=2;		//difference in speed when ghosts are edible (every GHOST_SPEED_REDUCTION, a ghost remains stationary)
	
	public static final Random rnd=new Random();
	
	public Game copy();												//returns an exact copy of the game (forward model)
	/**
	 * Advances the game using the actions (directions) supplied; 
	 * @param pacManDir
	 * @param ghostDirs
	 * @return all directions played [PacMan, Ghost1, Ghost2, Ghost3, Ghost4]
	 */
	public int[] advanceGame(int pacManDir,int[] ghostDirs);		
	/**
	 * Returns the reverse of the direction supplied
	 * @param direction 
	 * @return Typ {@link Integer}; Direction
	 */
	public int getReverse(int direction);							
	/**
	 * Returns bolean value if the Game is Over or not. The Game is over if Pac Man lost all her lives or 
	 * if MAX_LEVELS has been reached
	 * @return Typ {@link Boolean}
	 */
	public boolean gameOver();										
	/**
	 * Checks if the pill specified is still available
	 * @param pillIndex
	 * @return Typ {@link Boolean}
	 */
	public boolean checkPill(int pillIndex);						
	/**
	 * Checks if the power pill specified is still available
	 * @param powerPillIndex index for the power pill
	 * @return Typ {@link Boolean}
	 */
	public boolean checkPowerPill(int powerPillIndex);				
	/**
	 * Returns an array of size 4, indicating neighbouring nodes for the current position of Ms Pac-Man. E.g., [-1,12,-1,44] for neighbours 12 and 44 in direction RIGHT and LEFT
	 * @return Typ {@link Integer[]}; 
	 */
	public int[] getPacManNeighbours();	
	/**
	 * Returns an array of size 4, indicating neighbouring nodes for the current position of the ghost specified. 
	 * Replaces the direction corresponding to the opposite previous direction with -1
	 * @param whichGhost index of the Ghost
	 * @return Typ {@link Integer[4]}
	 */
	public int[] getGhostNeighbours(int whichGhost);				
	/**
	 * Return the current level
	 * 
	 * @return Typ {@link Integer}
	 */
	public int getCurLevel();										
	/**
	 * Returns the current maze
	 * 
	 * @return Typ {@link Integer}
	 */
	public int getCurMaze();										
	/**
	 * Return the node index where the Pac-Man is at
	 * @return Typ {@link Integer}
	 */
	public int getCurPacManLoc();									
	/**
	 * Return the last direction taken by Pac-Man
	 * @return Typ {@link Integer}
	 */
	public int getCurPacManDir();									
	/**
	 * Returns the number of lives remaining for Pac-Man
	 * @return Typ {@link Integer}
	 */
	public int getLivesRemaining();									
	/**
	 * Return the node index for the ghost specified
	 * 
	 * @param whichGhost, index of the ghost
	 * @return Typ {@link Integer};
	 */
	public int getCurGhostLoc(int whichGhost);	
	/**
	 * Returns the last direction taken by the ghost specified
	 * 
	 * @param whichGhost
	 * @return Typ {@link Integer}; direction
	 */
	public int getCurGhostDir(int whichGhost);					
	/**
	 * Returns the edible time (time left in which the ghost can be eaten) for the ghost specified
	 * @param whichGhost
	 * @return Typ {@link Integer}; (value x DELAY) ms 
	 */
	public int getEdibleTime(int whichGhost);						
	/**
	 * Returns an boolean value if the ghost is edible or not 
	 * @param whichGhost
	 * @return Typ {@link Boolean}; true if the ghost is currently edible
	 */
	public boolean isEdible(int whichGhost);						
	/**
	 * Returns the score of the game
	 * @return Typ {@link Integer}
	 */
	public int getScore();											
	/**
	 * Returns the time for which the CURRENT level has been played
	 * @return Typ {@link Integer};
	 */
	public int getLevelTime();										
	/**
	 * Returns the time for which the game has been played (across all levels)
	 * @return Typ {@link Integer}
	 */
	public int getTotalTime();										
	/**
	 * Returns the total number of pills in this maze (at the beginning of the level)
	 * @return Typ {@link Integer}
	 */
	public int getNumberPills();	
	/**
	 * Returns the total number of power pills in this maze (at the beginning of the level)
	 * @return Typ {@link Integer}
	 */
	public int getNumberPowerPills();	
	/**
	 * Returns the time remaining the ghost specified spends in the lair
	 * @param whichGhost
	 * @return Typ {@link Integer}; (value x DELAY) ms
	 */
	public int getLairTime(int whichGhost);							
	/**
	 * Returns true of ghost is at a junction and a direction is needed
	 * @param whichGhost
	 * @return Typ {@link Boolean}
	 */
	public boolean ghostRequiresAction(int whichGhost);				
	/**
	 * Returns the name of the maze
	 * @return Typ {@link String}
	 */
	public String getName();										
	/**
	 * Returns the position where Ms Pac-Man starts at the beginning of the level
	 * @return Typ {@link Integer}; Node index
	 */
	public int getInitialPacPosition();								
	/**
	 * Returns the position where the ghosts starts at the beginning of the level, AFTER leaving the lair
	 * @return Typ {@link Integer}; Nodei index
	 */
	public int getInitialGhostsPosition();							
	/**
	 * Returns the total number of nodes in the graph (pills, power pills and empty)
	 * @return Typ {@link Integer}
	 */
	public int getNumberOfNodes();									
	/**
	 * Returns the x-coordinate of the node specified
	 * @param nodeIndex 
	 * @return Typ {@link Integer}; X- Coordinate of the index 
	 */
	public int getX(int nodeIndex);									
	/**
	 * Returns the y-coordinate of the node specified
	 * @param nodeIndex
	 * @return Typ {@link Integer}; Y-Coordinate ot the index
	 */
	public int getY(int nodeIndex);									
	/**
	 * Returns the pill index of the node specified (can be used with the bitset for the pills)
	 * @param nodeIndex
	 * @return Typ {@link Integer}
	 */
	public int getPillIndex(int nodeIndex);							
	/**
	 * Returns the power pill index of the node specified (can be used with the bitset for the power pills)
	 * @param nodeIndex
	 * @return Typ {@link Integer}
	 */
	public int getPowerPillIndex(int nodeIndex);					
	/**
	 * Return the neigbour of the node specified for the direction supplied
	 * @param nodeIndex, the current nodeIndex
	 * @param direction, the directon from the player
	 * @return Typ {@link Integer}
	 */
	public int getNeighbour(int nodeIndex,int direction);			
	/**
	 * Returns indicies to all nodes with pills. 
	 * The indicies does not delete if the Pac-Man eat them.
	 * 
	 * @return Typ {@link Integer[]}
	 */
	public int[] getPillIndices();									
	/**
	 * Returns indices to all nodes with power pills
	 * @return Typ {@link Integer}
	 */
	public int[] getPowerPillIndices();									
	/**
	 * Returns indices to all nodes that are junctions 
	 * @return Typ {@link Integer}
	 */
	public int[] getJunctionIndices();								
		
	/**
	 * Returns the score awarded for the next to be eaten 
	 * @return Typ {@link Integer}; score
	 */
	public int getNextEdibleGhostScore();							
	/**
	 * Return the number of pills still in the maze
	 * 
	 * @return Typ {@link Integer}; The Pill Index (not Node index)
	 */
	public int getNumActivePills();									
	/**
	 * Returns the number of power pills still in the maze
	 * @return Typ {@link Integer}
	 */
	public int getNumActivePowerPills();							
	/**
	 * Returns the indices of all active pills in the maze
	 * @return Typ {@link Integer[]}
	 */
	public int[] getPillIndicesActive();							
	/**
	 * Returns the indices of all active power pills in the maze
	 * @return Typ {@link Integer}
	 */
	public int[] getPowerPillIndicesActive();						
	
	/**
	 * Returns Boolean value if the Node is a junction 
	 * @param nodeIndex
	 * @return Typ {@link Boolean}; true if node is a junction (more than 2 neighbours)
	 */
	public boolean isJunction(int nodeIndex);						
	/**
	 * Returns the number of neighbours of the node specified
	 * @param nodeIndex
	 * @return Typ {@link Integer}
	 */
	public int getNumNeighbours(int nodeIndex);						
	
	/**
	 * simple enumeration for use with the direction methods (below)
	 */
	public enum DM{PATH,EUCLID,MANHATTEN};				 			
	/**
	 * Returns the direction Ms Pac-Man should take to approach/retreat from the node specified, using the distance measure specified
	 * @param to
	 * @param closer
	 * @param measure Typ {@link DM}
	 * @return Typ {@link Integer}; direction
	 */
	public int getNextPacManDir(int to,boolean closer,DM measure);	
	/**
	 * returns the direction the ghost specified should take to approach/retreat from the node specified, using the distance measure specified
	 * @param whichGhost
	 * @param to
	 * @param closer
	 * @param measure Typ {@link DM}
	 * @return Typ {@link Integer}; direction
	 */
	public int getNextGhostDir(int whichGhost,int to,boolean closer,DM measure);	
	
	/**
	 * Returns the shortest path distance (Dijkstra) from one node to another
	 * @param from; node index
	 * @param to; node index
	 * @return Typ {@link Integer}; next direction
	 */
	public int getPathDistance(int from,int to);					
	/**
	 * Returns the Euclidean distance between two nodes
	 * @param from; node index
	 * @param to; node index
	 * @return Typ {@link Double}; euclid distance between the nodes
	 */
	public double getEuclideanDistance(int from,int to);	
	/**
	 * Returns the Manhatten distance between two nodes
	 * @param from; node index
	 * @param to; node index
	 * @return Typ {@link Integer}; manhatten distance between the nodes
	 */
	public int getManhattenDistance(int from,int to);				
	
	/**
	 * Returns the set of possible directions for Ms Pac-Man, with or without the direction opposite to the last direction taken
	 * @param includeReverse
	 * @return Typ {@link Integer}; possible next directories
	 */
	public int[] getPossiblePacManDirs(boolean includeReverse);		
	/**
	 * Returns the set of possible directions for the ghost specified (excludes the opposite of the previous direction)
	 * @param whichGhost
	 * @return
	 */
	public int[] getPossibleGhostDirs(int whichGhost);				

	/**
	 * Returns the path from one node to another (e.g., [1,2,5,7,9] for 1 to 9)
	 * @param from
	 * @param to
	 * @return Typ {@link Integer}; Array with Node indexes 
	 */
	public int[] getPath(int from,int to);										
	/**
	 * Returns the path from one node to another, taking into account that reversals are not possible
	 * @param whichGhost
	 * @param to
	 * @return Typ {@link Integer}; Array with Node indexes
	 */
	public int[] getGhostPath(int whichGhost,int to);							
	/**
	 * Selects a target from 'targets' given current position ('from'), a distance measure and whether it should be the point closest or farthest
	 * @param from Typ {@link Integer}; Node index
	 * @param targets Typ {@link Integer[]}; Array of Node indexes
	 * @param nearest Typ {@link Boolean}; select the nearest target
	 * @param measure Typ {@link DM}
	 * @return Typ {@link Integer}; one of the target
	 */
	public int getTarget(int from,int[] targets,boolean nearest,DM measure);	
	/**
	 * Selects a target for a ghost (accounts for the fact that ghosts may not reverse)
	 * @param from
	 * @param targets
	 * @param nearest
	 * @return Typ {@link Integer}; one of the target
	 */
	public int getGhostTarget(int from,int[] targets,boolean nearest);		
	/**
	 * Returns the distance of a path for the ghost specified (accounts for the fact that ghosts may not reverse)
	 * @param whichGhost
	 * @param to node index
	 * @return Typ {@link Integer}; Path length
	 */
	public int getGhostPathDistance(int whichGhost,int to);					
}