package yatzy;

import java.util.Arrays;
import java.util.List;

public enum Rule {

	ONES(new SumOfEyesWith(1)), //
	TWOS(new SumOfEyesWith(2)), //
	THREES(new SumOfEyesWith(3)), //
	FOURS(new SumOfEyesWith(4)), //
	FIVES(new SumOfEyesWith(5)), //
	SIXES(new SumOfEyesWith(6)), //
	BONUS(null), //
	THREE_OF_A_KIND(new ThreeOfAKind()), //
	FOUR_OF_A_KIND(new FourOfAKind()), //
	SMALL_STRAIGHT(null), //
	LARGE_STRAIGHT(null), //
	FULL_HOUSE(null), //
	CHANCE(null), //
	YATZY(null);

	interface Computation {
		public abstract int execute(Integer[] roll);
	}

	static class SumOfEyesWith implements Computation {
		private int sumForEyes;

		SumOfEyesWith(int sumForEyes) {
			this.sumForEyes = sumForEyes;
		}

		public int execute(Integer[] roll) {
			int sum = 0;
			for (Integer eyes : roll) {
				if (sumForEyes == eyes) {
					sum = sum + eyes;
				}
			}
			return sum;
		}
	}

	static class ThreeOfAKind implements Computation {
		public int execute(Integer[] roll) {
			Arrays.sort(roll);
			for (int index = 0; index < 3; index++) {
				if ((roll[index] == roll[index + 1]) && (roll[index + 1] == roll[index + 2])) {
					return roll[index] + roll[index + 1] + roll[index + 2];
				}
			}
			return 0;
		}
	}

	static class FourOfAKind implements Computation {
		public int execute(Integer[] roll) {
			Arrays.sort(roll);
			for (int index = 0; index < 2; index++) {
				if ((roll[index] == roll[index + 1]) && (roll[index + 1] == roll[index + 2]) && (roll[index + 2] == roll[index + 3])) {
					return roll[index] + roll[index + 1] + roll[index + 2] + roll[index + 3];
				}
			}
			return 0;
		}
	}

	Computation compute;

	private Rule(Computation compute) {
		this.compute = compute;
	}

	public int compute(Integer... roll) {
		return compute.execute(roll);
	}

	public int compute(List<Integer> roll) {
		return compute(roll.toArray(new Integer[5]));
	}
}
