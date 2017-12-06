package DAL;

import BLL.item.ItemStack;
import BLL.world.LockedBlacksmithPlanet;
import BLL.world.Planet;
import DAL.ACQ.Loadable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Handles the planets. It loads the planets.
 */
class PlanetHandler implements Loadable {
	private Map<String, Planet> planets;
	private Model model;

	PlanetHandler(Model model) {
		this.planets = null;
		this.model = model;

	}

	/**
	 * Gets the planets as a map.
	 * Key is planet name and value is the planet object.
	 * @return the map of planets
	 */
	Map<String, Planet> getPlanets() {
		return planets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load() throws IOException {
		Planet cleron, scurn, hebrilles, xehna, gallifrey,
				skaro, orion, deineax, uskillion, ayrus, amrit, earth;

		/* initializing planets */

		cleron = new Planet("Cleron OR7", "A blue and orange gas planet. A beautiful view!", "./src/DAL/resource/images/planetViews/CleronOr7.jpg", "./src/DAL/resource/images/planet2DMaps/cleron.jpg", 4578, 7000);
		scurn = new Planet("Scurn 01K", "Uninhabitable planet, however it's possible to be on the planet with a suit.", "./src/DAL/resource/images/planetViews/Scurn 01k.jpg","./src/DAL/resource/images/planet2DMaps/scurn.jpg", 6240, 7313);
		hebrilles = new Planet("Hebrilles", "Beneath the atmosphere, a beautiful crystallized sea can be seen.", "./src/DAL/resource/images/planetViews/Hebrilles.png","./src/DAL/resource/images/planet2DMaps/hebrilles.jpg",7100, 5310);
		xehna = new LockedBlacksmithPlanet("Xehna", "Home planet and populated by female amazons. The name comes from the Warrior Princess, Xena.", "./src/DAL/resource/images/planetViews/Xehna.jpg","./src/DAL/resource/images/planet2DMaps/xehna.jpg",6580, 3029);
		gallifrey = new Planet("Gallifrey", "Home planet of the almost extinct species, Time Lords, except the last one, Doctor Who.", "./src/DAL/resource/images/planetViews/Gallifrey.jpg", "./src/DAL/resource/images/planet2DMaps/gallifrey.jpg",6940, 1288);
		skaro = new Planet("Skaro", "A terrifying planet, conquered by the destroyers named Daleks.", "./src/DAL/resource/images/planetViews/scaro.jpg","./src/DAL/resource/images/planet2DMaps/skaro.jpg",5116, 1556);
		orion = new Planet("Orion", "The planet is originally from the constellation called Orion's Belt. It travelled into our Solar System.","./src/DAL/resource/images/planetViews/Orion.jpg", "./src/DAL/resource/images/planet2DMaps/orion.jpg", 3354, 1502);
		deineax = new Planet("Deineax", "No one knows exactly the origin behind the name of this planet, yet myths say it comes from 'Dennis'.", "./src/DAL/resource/images/planetViews/Deinax.jpg", "./src/DAL/resource/images/planet2DMaps/deineax.jpg",1619, 785);
		uskillion = new Planet("Uskillon", "An extremely hot planet, yet it contains different types of liquids.", "./src/DAL/resource/images/planetViews/Uskillon.jpg", "./src/DAL/resource/images/planet2DMaps/uskillon.jpg",1221, 2628);
		ayrus = new Planet("J8 Ayrus Z420", "No one knows what this planet contains. Secrets...", "./src/DAL/resource/images/planetViews/J8ayrus.jpg", "./src/DAL/resource/images/planet2DMaps/ayrus.jpg",1119, 4970);
		amrit = new Planet("Amrif Arret", "A habitable planet, covered with Mother Nature. Backwards, it is Terra Firma hence the content.", "./src/DAL/resource/images/planetViews/amrifarret.jpg", "./src/DAL/resource/images/planet2DMaps/amrifarret.jpg",2041, 6821);
		earth = new Planet("New Earth", "Humans tried to repopulate another planet and named it New Earth. You destroyed the original Earth, before it happened.", "./src/DAL/resource/images/planetViews/new earth.jpg", "./src/DAL/resource/images/planet2DMaps/earth.jpg",2995, 5600);

		/* adding items to planets */

		cleron.addItemStack(new ItemStack(model.getItemById(0)));
		cleron.addItemStack(new ItemStack(model.getItemById(14)));
		cleron.addItemStack(new ItemStack(model.getItemById(26)));
		cleron.addItemStack(new ItemStack(model.getItemById(40)));
		cleron.addItemStack(new ItemStack(model.getItemById(39)));

		scurn.addItemStack(new ItemStack(model.getItemById(1)));
		scurn.addItemStack(new ItemStack(model.getItemById(15)));
		scurn.addItemStack(new ItemStack(model.getItemById(27)));
		scurn.addItemStack(new ItemStack(model.getItemById(41)));
		scurn.addItemStack(new ItemStack(model.getItemById(54)));

		hebrilles.addItemStack(new ItemStack(model.getItemById(2)));
		hebrilles.addItemStack(new ItemStack(model.getItemById(16)));
		hebrilles.addItemStack(new ItemStack(model.getItemById(28)));
		hebrilles.addItemStack(new ItemStack(model.getItemById(42)));
		hebrilles.addItemStack(new ItemStack(model.getItemById(55)));

		xehna.addItemStack(new ItemStack(model.getItemById(3)));
		xehna.addItemStack(new ItemStack(model.getItemById(17)));
		xehna.addItemStack(new ItemStack(model.getItemById(29)));
		xehna.addItemStack(new ItemStack(model.getItemById(43)));
		xehna.addItemStack(new ItemStack(model.getItemById(53)));

		gallifrey.addItemStack(new ItemStack(model.getItemById(4)));
		gallifrey.addItemStack(new ItemStack(model.getItemById(18)));
		gallifrey.addItemStack(new ItemStack(model.getItemById(30)));
		gallifrey.addItemStack(new ItemStack(model.getItemById(44)));

		skaro.addItemStack(new ItemStack(model.getItemById(5)));
		skaro.addItemStack(new ItemStack(model.getItemById(19)));
		skaro.addItemStack(new ItemStack(model.getItemById(31)));
		skaro.addItemStack(new ItemStack(model.getItemById(45)));

		orion.addItemStack(new ItemStack(model.getItemById(6)));
		orion.addItemStack(new ItemStack(model.getItemById(20)));
		orion.addItemStack(new ItemStack(model.getItemById(32)));
		orion.addItemStack(new ItemStack(model.getItemById(46)));

		deineax.addItemStack(new ItemStack(model.getItemById(7)));
		deineax.addItemStack(new ItemStack(model.getItemById(21)));
		deineax.addItemStack(new ItemStack(model.getItemById(33)));
		deineax.addItemStack(new ItemStack(model.getItemById(47)));

		uskillion.addItemStack(new ItemStack(model.getItemById(8)));
		uskillion.addItemStack(new ItemStack(model.getItemById(22)));
		uskillion.addItemStack(new ItemStack(model.getItemById(34)));
		uskillion.addItemStack(new ItemStack(model.getItemById(48)));

		ayrus.addItemStack(new ItemStack(model.getItemById(9)));
		ayrus.addItemStack(new ItemStack(model.getItemById(23)));
		ayrus.addItemStack(new ItemStack(model.getItemById(35)));
		ayrus.addItemStack(new ItemStack(model.getItemById(49)));
		ayrus.addItemStack(new ItemStack(model.getItemById(13)));

		amrit.addItemStack(new ItemStack(model.getItemById(10)));
		amrit.addItemStack(new ItemStack(model.getItemById(24)));
		amrit.addItemStack(new ItemStack(model.getItemById(36)));
		amrit.addItemStack(new ItemStack(model.getItemById(50)));
		amrit.addItemStack(new ItemStack(model.getItemById(12)));

		earth.addItemStack(new ItemStack(model.getItemById(11)));
		earth.addItemStack(new ItemStack(model.getItemById(25)));
		earth.addItemStack(new ItemStack(model.getItemById(37)));
		earth.addItemStack(new ItemStack(model.getItemById(51)));
		earth.addItemStack(new ItemStack(model.getItemById(52)));
		earth.addItemStack(new ItemStack(model.getItemById(38)));

		/* shuffle the planets and put it inside a map */

		Planet[] planets = new Planet[]{
				cleron, scurn, hebrilles, xehna, gallifrey, skaro, orion,
				deineax, uskillion, ayrus, amrit, earth
		};

		Collections.shuffle(Arrays.asList(planets));

		this.planets = new LinkedHashMap<>();

		for (Planet planet : planets) {
			this.planets.put(planet.getName().replaceAll(" ", "").toLowerCase(), planet);
		}
	}
}