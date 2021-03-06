package DAL;

import BLL.scoring.Score;
import DAL.yaml.IOYaml;

import java.io.*;
import java.util.*;

/**
 * Handles the highscore. It saves and loads the highscore.
 */
class HighscoreHandler implements Savable, Loadable {
	private IOYaml ioYaml;
	private List<Score> highscore;

	/**
	 * Constructs a Highscore handler.
	 * @param file the location of the highscore file
	 */
	HighscoreHandler(File file) {
		this.ioYaml = new IOYaml(file);
		this.highscore = null;
	}

	/**
	 * Gets the highscore list.
	 * @return the list of scores
	 */
	List<Score> getHighscore() {
		return highscore;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load() throws FileNotFoundException {
		Map<Integer, Map<String, Object>> map = ioYaml.getYaml().load(new FileReader(ioYaml.getFile()));

		if(!map.isEmpty()) {
			highscore = new ArrayList<>();

			String name;
			int score;

			for (Map<String, Object> o : map.values()) {
				name = (String) o.get("name");
				score = (int) o.get("score");

				highscore.add(new Score(name, score));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save() throws IOException {
		Map<Integer, HashMap<String, Object>> map = new HashMap<>();

		for (int i = 0; i < highscore.size(); i++) {
			HashMap<String, Object> data = new HashMap<>();

			data.put("name", highscore.get(i).getName());
			data.put("score", highscore.get(i).getScore());

			map.put(i, data);
		}

		ioYaml.getYaml().dump(map, new FileWriter(ioYaml.getFile()));
	}
}
