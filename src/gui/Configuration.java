package gui;

import game.controllers.GhostController;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

/**
 * This class holds all registered players and contains methods for the creation
 * of new players.
 * 
 * @author Becker
 */
public class Configuration {

	/**
	 * The class references to all players found in the
	 * <code>poker.players</code> package.
	 */
	private static final ArrayList<Class<? extends AbstractPlayer>> players = new ArrayList<Class<? extends AbstractPlayer>>();
	private static final ArrayList<Class<? extends AbstractGhost>> ghosts = new ArrayList<Class<? extends AbstractGhost>>();

	/**
	 * The player names of all players available in the {@link #players} list.
	 * Additionally it contains an entry for a free seat.
	 */
	private static final ArrayList<String> playerNames = new ArrayList<String>();

	private static final ArrayList<String> ghostTeamNames = new ArrayList<String>();

	static {
		Configuration.loadAvailablePlayers();
		Configuration.loadAvailableGhosts();
	}

	/**
	 * This method returns a new instance of the spezified player.
	 * 
	 * @param classId
	 *            The ID specifying a player in the internal list.
	 * @return New {@link AbstractPlayer} object.
	 */
	public static AbstractPlayer getPlayer(int selectedIndex) {
		try {
			return players.get(selectedIndex-1).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static GhostController getGhost(int selectedIndex) {
		try {
			return ghosts.get(selectedIndex-1).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method used to get the names for the players including a "No Player"
	 * entry in the first component.
	 * 
	 * @return The names of the players.
	 */
	public static String[] getPlayerNames() {
		return playerNames.toArray(new String[playerNames.size()]);
	}

	public static String[] getGhostName() {
		return ghostTeamNames.toArray(new String[ghostTeamNames.size()]);
	}

	private static void loadAvailableGhosts() {
		if (!ghosts.isEmpty()) {
			System.out.println("Ghosts are already loaded!");
			return;
		}

		String curPackage = "game.player.ghost";
		try {
			/* Check all URLs to the package poker.players. */
			// Enumeration<URL> classUrls = ClassLoader.getSystemClassLoader()
			// .getResources(curPackage.replace(".", "/"));
			Enumeration<URL> classUrls = ClassLoader.getSystemClassLoader()
					.getResources(
							curPackage.replace(".",
									System.getProperty("file.separator")));
			while (classUrls.hasMoreElements()) {
				try {
					loadGhostFromDirectory(classUrls.nextElement().toURI()
							.getPath(), curPackage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		Collections.sort(ghosts,
				new Comparator<Class<? extends AbstractGhost>>() {

					@Override
					public int compare(Class<? extends AbstractGhost> o1,
							Class<? extends AbstractGhost> o2) {
						return o1.getSimpleName().compareTo(o2.getSimpleName());
					}

				});

		ghostTeamNames.add("No Ghost Team");
		for (Class<? extends AbstractGhost> clazz : ghosts) {
			try {
				ghostTeamNames.add(clazz.newInstance().getGhostGroupName());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void loadGhostFromDirectory(String curDirectory, String curPackage) {
		for (File file : new File(curDirectory).listFiles()) {
			try {
				if (file.isDirectory()) {
					/* Check the subpackages for further AbstractPlayer classes. */
					loadGhostFromDirectory(file.getAbsolutePath(), curPackage
							+ "." + file.getName());
				} else if (file.getName().endsWith(".class")) {
					/*
					 * Load the Class object and check if it is a
					 * AbstractPlayer.
					 */
					Class<?> clazz = Class.forName(curPackage
							+ "."
							+ file.getName().substring(0,
									file.getName().length() - 6));
					if (AbstractGhost.class.isAssignableFrom(clazz)) {
						ghosts.add((Class<? extends AbstractGhost>) clazz);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
	}

	private static void loadAvailablePlayers() {
		if (!players.isEmpty()) {
			System.out.println("Players are already loaded!");
			return;
		}

		String curPackage = "game.player.pacman";
		try {
			/* Check all URLs to the package poker.players. */
			// Enumeration<URL> classUrls = ClassLoader.getSystemClassLoader()
			// .getResources(curPackage.replace(".", "/"));
			Enumeration<URL> classUrls = ClassLoader.getSystemClassLoader()
					.getResources(
							curPackage.replace(".",
									System.getProperty("file.separator")));
			while (classUrls.hasMoreElements()) {
				try {
					loadPlayersFromDirectory(classUrls.nextElement().toURI()
							.getPath(), curPackage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		Collections.sort(players,
				new Comparator<Class<? extends AbstractPlayer>>() {

					@Override
					public int compare(Class<? extends AbstractPlayer> o1,
							Class<? extends AbstractPlayer> o2) {
						return o1.getSimpleName().compareTo(o2.getSimpleName());
					}

				});

		playerNames.add("No Player");
		for (Class<? extends AbstractPlayer> clazz : players) {
			try {
				playerNames.add(clazz.newInstance().getGroupName());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void loadPlayersFromDirectory(String curDirectory,
			String curPackage) {
		for (File file : new File(curDirectory).listFiles()) {
			try {
				if (file.isDirectory()) {
					/* Check the subpackages for further AbstractPlayer classes. */
					loadPlayersFromDirectory(file.getAbsolutePath(), curPackage
							+ "." + file.getName());
				} else if (file.getName().endsWith(".class")) {
					/*
					 * Load the Class object and check if it is a
					 * AbstractPlayer.
					 */
					Class<?> clazz = Class.forName(curPackage
							+ "."
							+ file.getName().substring(0,
									file.getName().length() - 6));
					if (AbstractPlayer.class.isAssignableFrom(clazz)) {
						players.add((Class<? extends AbstractPlayer>) clazz);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

}
