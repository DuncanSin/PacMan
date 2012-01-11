package gui;

import game.controllers.PacManController;

/**
 * This class extend all Pac-Man player to make it visible on the ui 
 */
public abstract class AbstractPlayer implements PacManController {

	
	
	/**
	 * Returns the groupname to select from combobox
	 * 
	 * @return Typ String; name of the group
	 */
	public abstract String getGroupName();
	
}
