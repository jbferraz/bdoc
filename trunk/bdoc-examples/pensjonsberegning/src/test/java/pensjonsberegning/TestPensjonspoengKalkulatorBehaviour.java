package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Link;
import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Spec;
import pensjonsberegning.bdoc.Story;

/**
 * http://no.wikipedia.org/wiki/Pensjonspoeng
 * http://www.nav.no/1073750161.cms?kapittel=10
 * http://www.nav.no/1073750161.cms?kapittel=11 detaljert:
 * http://www.nav.no/rettskildene/Rundskriv/147945.cms
 * http://www.nav.no/rettskildene/Rundskriv/147955.cms Bra:
 * http://www.dinside.no/1671/hva-faar-jeg-i-pensjon
 * 
 * Liste over grunnbelop:
 * http://www.consis.no/Endring+av+grunnbel%C3%B8pet+(G).FglLG2m.ips
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(PensjonspoengKalkulator.class)
public class TestPensjonspoengKalkulatorBehaviour {

	private GrunnbeloepRepository grunnbeloepRepository = new GrunnbeloepRepository();
	private PensjonspoengKalkulator pensjonspoengKalkulator = new PensjonspoengKalkulator(grunnbeloepRepository);

	@Test
	@Spec("inntekt - G / G = pensjonspoeng")
	@Link( { "http://no.wikipedia.org/wiki/Pensjonspoeng", "http://www.nav.no/1073750161.cms?kapittel=10" })
	public void forInntektInntil6GErPensjonspoengsberegningenFoelgende$spec$() {
		assertEquals(4.79, pensjonspoengKalkulator.beregn(new Inntekt(2008, 400000)), .001);
		assertEquals(3.34, pensjonspoengKalkulator.beregn(new Inntekt(2008, 300000)), .001);
	}

	@Test
	@Spec(": ((6 * G) + ((inntekt - (6 * G)) / 3) - G ) / G = pensjonspoeng")
	public void forInntektMellom6Og12GErPensjonsberegningenFoelgende$spec$() {
		assertEquals("Maks antall poeng", 7.00, pensjonspoengKalkulator.beregn(new Inntekt(2008, 829296)), .001);
		assertEquals("Lønn over 6G, men under 12G", 6.52, pensjonspoengKalkulator.beregn(new Inntekt(2008, 729296)), .001);
	}

	@Test
	public void forInntektIAarene1971Til1991BlirDenDelenAvInntektenSomOverstigerAatteGangerGrunnbeloepetMedregnetMedEnTredjedel() {
		assertEquals(8.33, pensjonspoengKalkulator.beregn(new Inntekt(1971, 12 * GrunnbeloepRepository._1971)), .001);
		assertEquals(7.00, pensjonspoengKalkulator.beregn(new Inntekt(1971, 8 * GrunnbeloepRepository._1971)), .001);
		assertEquals(7.67, pensjonspoengKalkulator.beregn(new Inntekt(1971, 10 * GrunnbeloepRepository._1971)), .001);
	}

	/**
	 * For årene 1971 - 1991 ble pensjonsgivende inntekt begrenset til tolv
	 * ganger grunnbeløpet. Den delen av inntekten som oversteg åtte ganger
	 * grunnbeløpet ble bare medregnet med en tredjedel. Som følge av dette var
	 * det høyeste oppnåelige poengtallet fra 1971 til og med 1991: 8,33
	 * 
	 * http://www.nav.no/rettskildene/Rundskriv/147945.cms
	 */

	@Test
	public void detMaksimaleAntalletPensjonspoengSomKanOpptjenesILoepetAvEtAarEr7() {
		assertEquals("Eksempel med lønn over 12G", 7, pensjonspoengKalkulator.beregn(new Inntekt(2008, 929296)), .001);
	}

	@Test
	@Spec("1 G")
	public void detGisKunPensjonspoengAvInntektPaaMerEnn$spec$() {
		assertEquals(0, pensjonspoengKalkulator.beregn(new Inntekt(2008, 30000)), .001);
		assertEquals(0, pensjonspoengKalkulator.beregn(new Inntekt(2008, 69108)), .001);
	}

	@Test
	public void skalBenytteGjennomsnittligGrunnbelopFraDetAaretInntektenGjaldt() {

		// => Lag testtabell som demonstrerer flere tilfeller: over under 6G og
		// på flere årstall
		assertEquals(3.58, pensjonspoengKalkulator.beregn(new Inntekt(2007, 300000)), .001);
		assertEquals(3.34, pensjonspoengKalkulator.beregn(new Inntekt(2008, 300000)), .001);
	}

	@Test
	@Spec(" 1967 - 1970 ")
	public void pensjonsgivendeInntektForAarene$spec$BlirRegnetMedInntektInntilAatteGangerGrunnbeloepet() {
		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1967, 4, 3);
		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1967, 8, 7);
		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1967, 12, 7);

		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1968, 8, 7);
		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1969, 8, 7);
		eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(1970, 8, 7);

	}

	void eksempelPaaUtregningAvPensjonspoengMellom1967Og1970(int inntektsaar, int inntektIGrunnbeloep, int pensjonspoeng) {
		Grunnbeloep grunnbeloep = grunnbeloepRepository.gjennomsnittligGrunnbeloepFor(inntektsaar);
		Inntekt inntekt = new Inntekt(inntektsaar, inntektIGrunnbeloep * grunnbeloep.verdi());
		String forklaring = "Forventet " + pensjonspoeng + " pensjonspoeng for inntektsaar " + inntektsaar;

		assertEquals(forklaring, pensjonspoeng, pensjonspoengKalkulator.beregn(inntekt), .001);
	}

}
