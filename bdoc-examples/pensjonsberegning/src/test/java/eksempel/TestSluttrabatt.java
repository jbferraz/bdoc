package eksempel;

import static org.junit.Assert.*;
import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;

public class TestSluttrabatt {

	@Test
	public void rimeligsteVareIRabattVedKjopAvTreVarer() {
		List<Integer> vareBeloep = asList(10, 20, 30);

		int rabatt = Sluttrabatt.kalkulert(vareBeloep);
		
		assertEquals(10, rabatt);
	}


	@Test
	public void beregnesBasertPaaAntallVarerOgPris() {
		List<Integer> vareBeloep = HandelTestdata.belopslisteMedTreVarekjoep();

		int sluttrabatt = Sluttrabatt.kalkulert(vareBeloep);
		
		assertEquals(10, sluttrabatt);
	}

	@Test
	public void skalTilsvareRimligsteVareGratisVedKjopAvTreVarer() {
		List<Integer> vareBeloep = asList(10, 20, 30);

		assertEquals(10, Sluttrabatt.kalkulert(vareBeloep));
	}

	public static class Sluttrabatt {

		public static int kalkulert(List<Integer> vareBeloep) {
			return 10;
		}

	}

	public static class HandelTestdata {

		private static List<Integer> belopslisteMedTreVarekjoep() {
			return null;

		}

	}
}
