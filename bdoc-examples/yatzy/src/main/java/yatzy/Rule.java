package yatzy;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.bag.TreeBag;

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
	FULL_HOUSE(new Computation() {
		public int execute(Integer[] roll) {
			Arrays.sort(roll);
			if (roll[0] != roll[1]) {
				return 0;
			}
			if (roll[3] != roll[4]) {
				return 0;
			}
			int sum = 0;
			for (Integer dice : roll) {
				sum += dice;
			}
			return sum;
		}
	}), //
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
			return sumOfNumberOfKinds(roll, 3);
		}
	}

	static class FourOfAKind implements Computation {
		public int execute(Integer[] roll) {
			return sumOfNumberOfKinds(roll, 4);
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

	protected static int sumOfNumberOfKinds(Integer[] roll, int numberOfKinds) {
		TreeBag treeBag = new TreeBag(Arrays.asList(roll));
		Set<Integer> uniqueSet = treeBag.uniqueSet();
		for (Integer eyes : uniqueSet) {
			int count = treeBag.getCount(eyes);
			if (count == numberOfKinds) {
				return numberOfKinds * eyes;
			}
		}
		return 0;
	}
}
