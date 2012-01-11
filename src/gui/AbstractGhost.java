package gui;

import game.controllers.GhostController;

/**
 * This class extend all ghost player to make it visible on the ui 
 */
public abstract class AbstractGhost implements GhostController{

	/**
	 * Returns the groupname to select from combobox
	 * 
	 * @return Typ String; name of the group
	 */
	public abstract String getGhostGroupName();
	
}
