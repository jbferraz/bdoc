package pensjonsberegning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestPensjonspoeng {
	private Pensjonspoeng pensjonspoeng = new Pensjonspoeng(new GrunnbeloepRepository());

	@Test
	public void skalKunneUtfoeresForPensjonsgivendeInntektMellom1967Og2008() {
		for (int aar = 1976; aar < 2009; aar++) {
			assertTrue(0 < pensjonspoeng.beregn(aar, 300000));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void skalGiFeilVedForsokPaaBeregningAvPoengtallFor1967ogEtter2008() {
		pensjonspoeng.beregn(1966, 300000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void skalGiFeilVedForsokPaaBeregningAvPoengtallEtter2008() {
		pensjonspoeng.beregn(2009, 300000);
	}	

}
