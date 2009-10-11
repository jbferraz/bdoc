package yatzy;

public enum Rule {

	ONES(new SumOfEyesWith(1)), //
	TWOS(new SumOfEyesWith(2)), //
	THREES(new SumOfEyesWith(3)), //
	FOURS(null), //
	FIVES(null), //
	SIXES(null), //
	BONUS(null), //
	THREE_OF_A_KIND(null), //
	FOUR_OF_A_KIND(null), //
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

	Computation compute;

	private Rule(Computation compute) {
		this.compute = compute;
	}

	public int compute(Integer... roll) {
		return compute.execute(roll);
	}
}
