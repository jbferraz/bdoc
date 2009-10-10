package yatzy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private Map<String, Integer> playerScore = new HashMap<String, Integer>();

	public int score(String player) {
		if (!playerScore.containsKey(player)) {
			return 0;
		}
		return playerScore.get(player);
	}

	public void addRoll(String player, List<Integer> roll) {
		int score = score(player);
		for (Integer dice : roll) {
			score = score + dice;
		}
		playerScore.put(player, score);
	}

}
