package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateMidnight;
import org.junit.Test;

import pensjonsberegning.bdoc.Link;
import pensjonsberegning.bdoc.Ref;
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
public class TestPensjonspoengKalkulatorBehaviour {

	private static final DateMidnight _2008 = new DateMidnight(2008, 1, 1);
	private static final DateMidnight _2007 = new DateMidnight(2007, 1, 1);

	private PensjonspoengKalkulator pensjonspoengKalkulator = new PensjonspoengKalkulator(new GrunnbeloepRepository());

	@Test
	@Spec("inntekt - G / G = pensjonspoeng")
	@Link( { "http://no.wikipedia.org/wiki/Pensjonspoeng","http://www.nav.no/1073750161.cms?kapittel=10" } )
	public void forInntektInntil6GErPensjonspoengsberegningenFoelgende$spec$() {
		assertEquals(4.79, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 400000)), .001);
		assertEquals(3.34, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 300000)), .001);
	}

	@Test
	@Spec(": ((6 * G) + ((inntekt - (6 * G)) / 3) - G ) / G = pensjonspoeng")
	public void forInntektMellom6Og12GErPensjonsberegningenFoelgende$spec$() {
		assertEquals("Lønn på 12 G", 7.00, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 829296)), .001);
		assertEquals("Lønn over 6G, men under 12G", 6.52, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 729296)), .001);
	}

	@Test
	public void detMaksimaleAntalletPensjonspoengSomKanOpptjenesILoepetAvEtAarEr7() {
		assertEquals("Eksempel med lønn over 12G", 7, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 929296)), .001);
	}

	@Test
	public void skalBenytteGjennomsnittligGrunnbelopFraDetAaretInntektenGjaldt() {

		// => Lag testtabell som demonstrerer flere tilfeller: over under 6G og
		// på flere årstall
		assertEquals(3.58, pensjonspoengKalkulator.beregnet(new Inntekt(_2007, 300000)), .001);
		assertEquals(3.34, pensjonspoengKalkulator.beregnet(new Inntekt(_2008, 300000)), .001);
	}

	// mangler regler for før etter 1992

}
