package game.player.pacman;

import game.core.Game;
import gui.AbstractPlayer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
 * Allows a human player to play the game using the arrow key of the keyboard.
 */
public class AbstractHuman extends AbstractPlayer{

    @Override
	public int getAction(Game game,long dueTime){
    	return -1;
    }
    
	@Override
	public String getGroupName() {
		return "Selbst spielen";
	}
	
}