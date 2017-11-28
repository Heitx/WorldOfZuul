package BLL;

import BLL.ACQ.*;
import BLL.character.Inventory;
import BLL.character.npc.NPC;
import BLL.character.player.Player;
import BLL.character.player.buff.Buff;
import BLL.item.Item;
import BLL.item.ItemStack;
import BLL.scoring.Score;
import BLL.scoring.ScoreHandler;
import BLL.world.Planet;
import UI.ConsoleView;
import UI.command.CommandWord;

import java.util.*;

public class Game implements Domain {
	private static Game INSTANCE;

	private ConsoleView view;
	private Persistent model;
	private UsableHandler usableHandler;

	private boolean finished;
	private boolean gameWon;
    private boolean trapped;
	private Player player;
	private NPCHandler npcHandler;
	private ScoreHandler scoreHandler;

	private Game() {
		view = new ConsoleView();
		usableHandler = new UsableHandler();

		finished = false;
		gameWon = false;
		player = new Player();
		npcHandler = new NPCHandler();
		scoreHandler = new ScoreHandler();
	}

	/**
	 * Injects an object of the {@link Persistent} interface.
	 * In additional it runs a few methods inside the persistent interface immediately.
	 * @param persistent the object implementing {@link Persistent} interface.
	 */
	@Override
	public void injectPersistent(Persistent persistent) {
		this.model = persistent;
		this.model.setUsableHandler(usableHandler);
		this.model.load();
		init();
	}

	/**
	 * Returns an object with {@link IPlayer}, which limits access of GUI.
	 * The function comes from the {@link Domain} interface.
	 * @return the player, but casted to {@link IPlayer}.
	 */
	@Override
	public IPlayer getPlayer() {
		return player;
	}

	/**
	 * Returns a map with all planets.
	 * The key is a planet name and the value is a {@link Planet}.
	 * @return a map of the planets and their associate names.
	 */
	@Override
	public Map<String, IPlanet> getPlayerPlanets() {
		return new HashMap<>(player.getPlanets());
	}

	/**
	 * Sets the finished boolean to a new value.
	 * It is used to determine whether the game finish.
	 * Default is false.
	 * @param finished a boolean to override current value.
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * Sets the game won boolean to a new value.
	 * It is used to determine whether the player has won the game or not.
	 * Default is false.
	 * @param gameWon a boolean to override current value.
	 */
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}

	/**
	 * An initialization of the business layer.
	 * Is invoked by {@link #injectPersistent(Persistent)} function.
	 */
	private void init() {
		Map<String, Planet> planetMap = model.getPlanets();
		Planet[] planets = planetMap.values().toArray(new Planet[planetMap.size()]);

		Planet centerUniverse = new Planet("Center of the Universe", "This is not exactly the center, since a black hole exists in the center of every Universe.", 0, 0);
		player.setCurrentPlanet(centerUniverse);
		player.setPlanets(planetMap);
		npcHandler.getBlacksmith().setCurrentPlanet(planets[(int) (Math.random() * planets.length)]);
		npcHandler.getBlacksmith().setPlanets(planetMap);
		npcHandler.getUnoX().setQuizes(model.getQuizes());
		//addCluesToPlanets();
        trapped = true;

        player.getCurrentPlanet().getNPCs().add(npcHandler.getProfessorPutricide());
	}

	/**
	 * Use to determine whether the player has given a correct answer from the GUI.
	 * Is invoked by GUI when an answer is given from the player.
	 * @param index is the answer of the multiple choice (exclude 0).
	 * @return a boolean whether the answer is true or not.
	 */
	@Override
	public boolean isAnswerCorrect(int index) {
		return npcHandler.getUnoX().isAnswerCorrect(index);
	}

	/**
	 * Gets the {@link BLL.character.player.Quiz} object as {@link IQuiz} to limit the functionality in GUI.
	 * @return returns the current quiz in play.
	 */
	@Override
	public IQuiz getQuiz() {
		npcHandler.getUnoX().pickRandomQuiz();
		return npcHandler.getUnoX().getCurrentQuiz();
	}

	/**
	 * If the itemstack's item is usable with {@link Item#hasUsable()} function,
	 * it will trigger the {@link Item#use(Player, Game)} function.
	 * @param iis the item stack containing the item.
	 * @return true, if it has a usable, false if it does not.
	 */
	@Override
	public boolean useItem(IItemStack iis) {
		if(iis.getIItem() instanceof Item) {
			Item i = (Item) iis.getIItem();

			if(i.hasUsable()) {
				i.use(player, this);
				return true;
			}
		}

		return false;
	}

	/**
	 * Use to determine the search state of the planet {@link SearchPlanetState}.
	 * In addition, it sets the permanent/temporary search of the current planet.
	 * @return the state given by the enum
	 */
	@Override
	public SearchPlanetState searchPlanet() {
		if(player.getCurrentPlanet().getTempSearched()) {
			return SearchPlanetState.ALREADY_SEARCHED;
		} else {
			player.getCurrentPlanet().setPermanentSearch(true);
			player.getCurrentPlanet().setTemporarySearch(true);

			if(player.samePlanet(npcHandler.getBlacksmith().getCurrentPlanet())) {
				return SearchPlanetState.BLACKSMITH;
			}
		}

		return SearchPlanetState.NOTHING;
	}

	/**
	 * It loops through the buff list of the player.
	 * On each buff, it triggers {@link Buff#onGameTick(Player)}
	 * and {@link Buff#isExpired()}, if true, removes the buff.
	 */
	@Override
	public void updateBuffs() {
		List<Buff> buffs = player.getBuffs();

		Buff buff;
		for(int i = 0; i < buffs.size(); i++) {
			buff = buffs.get(i);

			buff.onGameTick(player);

			if(buff.isExpired()) {
				player.removeBuff(i);
			}
		}
	}

	/**
	 * It triggers the start event of the action inside a specific NPC.
	 * At every action, the GUI must invoke this function first before doing anything.
	 * @param npc is the specific NPC for interaction
	 * @param actionId is the specific action of the NPC
	 */
	@Override
	public void startInteract(NPC npc, int actionId) {
		if(npc != null) {
			INPCAction[] actions = npc.getActions();
			actions[actionId].onStartEvent(player, npc, model);
		}
	}

	/**
	 * It triggers the end event of the action inside specific NPC.
	 * At every action, the GUI must invoke this function last.
	 * @param npc is the specific NPC for interaction
	 * @param actionId is the specific action of the NPC
	 */
	@Override
	public void endInteract(NPC npc, int actionId) {
		if(npc != null) {
			INPCAction[] actions = npc.getActions();
			actions[actionId].onEndEvent(player, npc, model);
		}


//
//					if(command.hasArguments()) {
//			view.println("Interact does not need any arguments.");
//		} else {
//			if(!player.getCurrentPlanet().getTempSearched()) {
//				view.println("You have not searched the planet!");
//			} else {
//				if(!blacksmith.samePlanet(player.getCurrentPlanet())) {
//					view.println("The blacksmith is not here.");
//				} else {
//                    int trappedInt = trapped ? 1 : 2;
//                    switch(trappedInt) {
//                        case 1:
//                            view.println(blacksmith.getLockedMsg());
//                            if(blacksmith.hasAccepted(view.getParser().getQuizOfferAnswer())) {
//                                player.decreaseFuel(10);
//                                view.println("You chose to help Gearhead! Fuel has decreased by 10!");
//                                trapped = false;
//                            }
//                            view.getParser().resetReader();
//                            break;
//                        case 2:
//                            view.println(blacksmith.getBlacksmithMsg());
//                            Recipe recipe = blacksmith.getRecipe();
//                            Item[] items = recipe.getRequirements();
//	                        ItemStack[] content = player.getInventory().getContent();
//                            boolean[] containItems = recipe.haveItems(content);
//
//                            for(int i = 0; i < items.length; i++) {
//                                view.println((containItems[i] ? "[\u2713] " : "[\u2715] ") + "XXXXXXXX ");
//                            }
//
//                            if(allTrue(containItems)) {
//                                // TODO: remove items from the players backpack
//	                            // TODO: change portal gun to be repaired
//                                view.println("");
//                                view.println("Portalgun has been repaired!");
//                            }
//                            break;
//                    }*/
//                    view.println(lockedBlacksmith.getBlacksmithMsg());
//					Recipe recipe = lockedBlacksmith.getRecipe();
//					Item[] items = recipe.getRequirements();
//					boolean[] containItems = recipe.haveItems(player.getInventory().getContent());
//
//					for(int i = 0; i < items.length; i++) {
//						view.println((containItems[i] ? "[\u2713] " : "[\u2715] ") + "XXXXXXXX " + items[i].getComponentType().name());
//					}
//
//					if(allTrue(containItems)) {
//						// TODO: remove items from the players backpack
//						view.println("");
//						view.println("Portalgun has been repaired!");
//						player.getInventory().getItemPortalGun().repair();
////					}
//				}
//			}
//		}
	}

	/**
	 * Picks an itemstack and sends it to the player's inventory.
	 * @param iis the item stack to add to the inventory
	 * @return true, if successful
	 */
	@Override
	public boolean pickupItem(IItemStack iis) {
		if(player.getCurrentPlanet().getPermSearched()) {
			if(iis.getIItem().isPickupable()) {
				Inventory bp = player.getInventory();
				ItemStack is = (ItemStack) iis;

				if(bp.add(is)) {
					player.getCurrentPlanet().removeItemStack(is);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Drops an itemstack from the player's inventory to the current planet.
	 * @param iis the item stack to drop from the inventory
	 * @return true, if successful
	 */
	@Override
	public boolean dropItem(IItemStack iis) {
		if(player.getCurrentPlanet().getPermSearched()) {
			if(iis.getIItem().isDropable()) {
				Inventory bp = player.getInventory();
				ItemStack is = (ItemStack) iis;

				if(bp.remove(is)) {
					player.getCurrentPlanet().addItemStack(is);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Moves the player to the specified planet.
	 * @param planetName the name of planet
	 * @return an enum based on the what happen {@link MovePlayerState}
	 */
	@Override
	public MovePlayerState movePlayerToPlanet(String planetName) {
		planetName = planetName.toLowerCase();

		Map<String, Planet> planets = player.getPlanets();

		if(!planets.containsKey(planetName)) {
			return MovePlayerState.NOT_VALID;
		} else if(player.samePlanet(planets.get(planetName))) {
			return MovePlayerState.SAME_PLANET;
		} else {
			player.go(planetName);
			player.decreaseFuel(10);
			player.getCurrentPlanet().setTemporarySearch(false);

			return MovePlayerState.SUCCESS;
		}
	}

	/**
	 * An method to decrease fuel when moving,
	 * if decrease of fuel on move is desired.
	 */
	@Override
	public void decreaseFuelOnMove(int ticksPerSecond) {
		player.decreaseFuel(0.3 / ticksPerSecond);
	}

	/**
	 * Add the clues to the planets at random.
	 */
	private void addCluesToPlanets(){
		Item[] items = npcHandler.getBlacksmith().getRecipe().getRequirements();
		Item[] clues = new Item[8];

		for (int i = 0; i < clues.length; i++) {
			clues[i] = model.getItemById(56);
		}

		Item item;
		Item clue;
		String s;
		String newDescription;

		for (int i = 0; i < items.length; i++) {
			item = items[i];
			for (int j = i * 2; j < i * 2 + 2; j++) {
				clue = clues[j];
//				clue.setColor(item.getColor());
//				clue.setState(item.getState());
//				clue.setComponentType(item.getComponentType());
				s = j % 2 == 0 ? "{{color}}" : "{{state}}";
				newDescription = clue.getDescription().replace("{{clue}}", s);
				clue.setDescription(newDescription);
			}
		}

		List<Planet> planetsList = new ArrayList<>(player.getPlanets().values());
		Collections.shuffle(planetsList);
		for (int i = 0; i < clues.length; i++) {
			planetsList.get(i).addItemStack(new ItemStack(clues[i]));
		}
	}

	// TODO: Perhaps change boolean to int to get the direct location

	/**
	 * Checks whether a player has beaten any highscore.
	 * @return true, if player has beaten a highscore.
	 */
	@Override
	public boolean hasBeatenHighscore() {
		int points = scoreHandler.calculatePoints(player.getTotalFuelConsumption());
		List<Score> scores = model.getHighscore();

		for(Score score : scores) {
			if(points > score.getScore()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds a player to the highscore, sorts the list,
	 * removes the 'unused' one and saves the highscore.
	 * @param playerName the name of the player
	 */
	@Override
	public void addPlayerToHighscore(String playerName) {
		int points = scoreHandler.calculatePoints(player.getTotalFuelConsumption());
		List<Score> scores = model.getHighscore();
		scores.add(new Score(playerName, points));
		Collections.sort(scores);
		scores.remove(scores.size() - 1);
		model.saveHighscore();
	}

	/**
	 * Gets the current highscore list as {@link IScore}.
	 * @return the highscore list
	 */
	@Override
	public List<IScore> getHighscore() {
		return new ArrayList<>(model.getHighscore());
	}

	/* function to print a welcome message */
	private String[] welcomeMessage() {
		return new String[] {
			"",
			"Welcome to the ridicoulous Rick & Morty spinoff!",
			"Rick & Morty spinoff is a new and incredibly addictive adventure game!",
			"[Type '" + CommandWord.HELP + "' if you need help]",
            "[Type '" + CommandWord.INFO + "' if you need more information]",
			"",
		};
	}
        
    private String[] descriptionMessage() {
        return new String[] {
                            "GAME DESCRIPTION",
			"--------------",
			"You are Rick, the brilliant scientist. But you have mistakenly destroyed Earth in your current dimension.",
			"Normally, you would use your Portal Gun to teleport yourself to a new dimension... But it's broken!",
			"Your mission is now to fix your Portal Gun and travel safely to a new dimension. Good luck!",
			"--------------",
                            "",
        };
    }
        
    private String[] hintMessage() {
        return new String[] {
            "OBJECTIVE:",
            "--------------",
            "> You need to find the blacksmith named Gearhead!",
            "> Gearhead will show you his recipe for the Portal Gun!",
            "> It is now your job to find all the items needed!",
            "> Return to Gearhead and repair your Portal Gun!",
            "--------------",
        };
    }

	/* function to print the help section */
	private String[] helpMessage() {
		return new String[] {
				"You are lost. You are alone.",
				"You wander around in the universe.",
				"",
				"Your command words are:",
				view.getParser().getAllCommands()
		};
	}

	private String[] argumentMessage(String usage) {
		return new String[]{
				"You have entered too many arguments!",
				"Usage: " + usage
		};
	}

	private boolean allTrue(boolean[] booleans) {
		for(boolean b : booleans) {
			if(!b) { return false; }
		}

		return true;
	}

	private void gameIsFinished() {
		StringBuilder sb = new StringBuilder();

		if(gameWon){
			double millisecondsElapsed = scoreHandler.calculateTimeElapsed();
			int seconds = (int) ((millisecondsElapsed / 1000) % 60);
			int minutes = (int) ((millisecondsElapsed / 1000) / 60);

			int points = scoreHandler.calculatePoints(player.getTotalFuelConsumption());
			int stars = scoreHandler.getStars(points);

			char[] earnedStars = new char[stars];
			Arrays.fill(earnedStars, '\u26e4');

			sb.append("---------------------- GAME WON! -----------------------\n");
			sb.append("Here are some stats for you to brag about...\n");
			sb.append("You played for ").append(minutes).append(":").append(seconds).append(" minutes\n");
			sb.append("Your total fuel consumption was ").append(player.getTotalFuelConsumption()).append(" liters\n");
			sb.append("Out of 5 stars ").append("you earned: ").append(earnedStars).append("\n");
			sb.append("--------------------------------------------------------");

			view.println(sb.toString());
		} else{
			sb.append("---------------------- GAME OVER! ----------------------\n");
			if(player.isFuelEmpty()){
				sb.append("You ran out of fuel!\n");
			}
			/*sb.append("If you want to play again - type '" + CommandWord.RESTART + "'\n");
			sb.append("If you want to quit - type '" + CommandWord.QUIT + "'\n");*/
			sb.append("--------------------------------------------------------");

			view.println(sb.toString());
		}
	}

	/**
	 * Gets the instance of the Game.
	 * *Singleton*
	 * @return the instance of game
	 */
	public static Game getInstance() {
		if(INSTANCE == null) INSTANCE = new Game();
		return INSTANCE;
	}
}
