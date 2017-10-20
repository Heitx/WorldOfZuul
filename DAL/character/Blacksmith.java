package DAL.character;

import DAL.world.Planet;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class Blacksmith extends Character {
	private String[] visitedPlanets;

	public Blacksmith() {
		visitedPlanets = new String[3]; // used for traces
	}

	public void move() {
		LinkedHashMap<String, Planet> map = (LinkedHashMap<String, Planet>) getPlanets();

		Iterator<Planet> iterator = map.values().iterator();

		while(iterator.hasNext()) {
			if(iterator.next() == getCurrentPlanet()) {
				if(iterator.hasNext()) {
					setCurrentPlanet(iterator.next());
				} else {
					setCurrentPlanet(map.values().iterator().next());
				}

				pushTraces();
				visitedPlanets[0] = getCurrentPlanet().getName().toLowerCase();

				break;
			}
		}

		System.out.println("Blacksmith: " + getCurrentPlanet().getName());
	}

	private void pushTraces() {
		for(int i = visitedPlanets.length - 1; i > 0; i--) {
			visitedPlanets[i] = visitedPlanets[i - 1];
		}
	}

	public String getVisitMessage(String planetName) {
		String msg;
		int traceIndex = -1;

		for(int i = 0; i < visitedPlanets.length; i++) {
			if(visitedPlanets[i] != null) {
				if(visitedPlanets[i].equals(planetName.toLowerCase())) {
					traceIndex = i;
					break;
				}
			}
		}

		switch(traceIndex) {
			case 0: msg = "The blacksmith is on the planet!";
				break;
			case 1: msg = "You just passed the blacksmith!";
				break;
			case 2: msg = "The blacksmith was here recently.";
				break;
			case 3: msg = "The time has passed and the trace of a blacksmith is almost gone!";
				break;
			default: msg = "The traces of the Blacksmith has disappeared!";
		}

		return msg;
	}
}
