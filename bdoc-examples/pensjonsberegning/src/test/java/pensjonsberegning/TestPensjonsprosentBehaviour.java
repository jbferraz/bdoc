package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Spec;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Pensjonsprosent.class)
public class TestPensjonsprosentBehaviour extends ScenarioSupport<TestPensjonsprosentBehaviour>{

	@Test
	public void forOpptjeningsaareneFraOgMed1967TilOgMed1991ErPensjonsprosenten45() {
		assertEquals(45, Pensjonsprosent.forAar(1967), .001);
		assertEquals(45, Pensjonsprosent.forAar(1991), .001);
	}

	@Test
	public void fraOgMedOpptjeningsaaret1992ErPensjonsprosenten42() {
		assertEquals(42, Pensjonsprosent.forAar(1992), .001);
		assertEquals(42, Pensjonsprosent.forAar(2009), .001);
	}

	@Test
	@Spec(" ( 45% * opptjenings�r i tiden 1967�1991 + 42% * opptjenings�r fra og med 1992) / 40")
	public void hovedregelVedPensjonsprosentberegningEr$spec$() {
		eksempel.beregningAvPensjonsprosent("Maksimal pensjonsprosent", 40, 0, 45d);
		eksempel.beregningAvPensjonsprosent("Kun 20 �r opptjening f�r 1992", 20, 0, 22.5d);
		eksempel.beregningAvPensjonsprosent("Full opptjening etter 1991", 0, 40, 42d);
		eksempel.beregningAvPensjonsprosent("Halv opptjening etter 1991", 0, 20, 21d);
		eksempel.beregningAvPensjonsprosent("Full opptjening fordelt likt fram til 1991 og etter", 20, 20, 43.5d);
	}

	@Test
	public void maksimaltAntallOpptjeningsaarSomMedregnesEr40() {
		eksempel.beregningAvPensjonsprosent("Ekstra opptjening (ut over 40 �r) fram til 1991 vil ikke medregnes", 41, 0, 45d);
		eksempel.beregningAvPensjonsprosent("Ekstra opptjening (ut over 40 �r) fra og med 1992 vil ikke medregnes", 0, 41, 42d);
	}

	@Test
	public void opptjeningsaarFoer1992TellesFoerstNaarSummenAvOpptjeningsaarFoerOgEtter1992Overstiger40() {
		eksempel.beregningAvPensjonsprosent("Maksimal pensjonsprosent, 1 �r etter 1991 tas ikke med", 40, 1, 45d);
		eksempel.beregningAvPensjonsprosent("Full opptjening, 2 opptjenings�r etter 1991 tas ikke med", 20, 22, 43.5d);
	}

	void beregningAvPensjonsprosent(String beskrivelse, int opptjeningsaarITiden1967Til1991,
			int opptjeningsaarFraOgMed1992, double pensjonsprosent) {

		double prosent = 100 * new Pensjonsprosent(opptjeningsaarITiden1967Til1991, opptjeningsaarFraOgMed1992).beregnet();
		assertEquals(pensjonsprosent, prosent, .001);
	}
}
