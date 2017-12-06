package BLL;

import BLL.entity.npc.Blacksmith;
import BLL.entity.npc.ProfessorPutricide;
import BLL.entity.npc.SpacePirate;
import BLL.entity.npc.actions.ProfessorPutricideAction;
import BLL.entity.npc.UnoX;

/**
 * Handles all the functions to NPCs (No Player Characters) within the game.
 * It contains all the references and all the get methods.
 * In addition it sets the needed {@link BLL.entity.npc.actions.NPCActionCollection} to each NPC.
 */
public class NPCHandler {
	private Blacksmith blacksmith;
	private ProfessorPutricide professorPutricide;
	private UnoX unoX;
	private SpacePirate pirate;

	NPCHandler() {
		blacksmith = new Blacksmith();
		professorPutricide = new ProfessorPutricide();
		unoX = new UnoX();
		pirate = new SpacePirate();

		initActions();
	}

	/**
	 * Inits all the actions to the NPCs.
	 */
	private void initActions() {
		professorPutricide.setActions(new ProfessorPutricideAction());
	}

	/**
	 * Gets the Blacksmith NPC.
	 * @return the blacksmith
	 */
	public Blacksmith getBlacksmith() {
		return blacksmith;
	}

	/**
	 * Gets the Professor Putricide NPC.
	 * @return the professor putricide
	 */
	public ProfessorPutricide getProfessorPutricide() {
		return professorPutricide;
	}

	/**
	 * Gets the Pirate NPC.
	 * @return the pirate
	 */
	public SpacePirate getPirate() {
		return pirate;
	}

	/**
	 * Gets the UnoX NPC.
	 * @return the unox
	 */
	public UnoX getUnoX() {
		return unoX;
	}
}
