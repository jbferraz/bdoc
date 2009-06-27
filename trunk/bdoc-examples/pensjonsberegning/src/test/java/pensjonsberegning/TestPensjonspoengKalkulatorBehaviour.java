package pensjonsberegning;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

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
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
public class TestPensjonspoengKalkulatorBehaviour {

	@Test
	@Spec("inntekt - G / G = pensjonspoeng")
	public void forInntektInntil6GErPensjonspoengsberegningenFoelgende$spec$() {
		assertEquals("Eksempel fra http://no.wikipedia.org/wiki/Pensjonspoeng", // 
				4.79, new PensjonspoengKalkulator(new Inntekt(400000), new GjennomsnittligGrunnbeloep(69108)).beregnet(), .001);

		assertEquals("Eksempel fra http://www.nav.no/1073750161.cms?kapittel=10", // 
				3.34, new PensjonspoengKalkulator(new Inntekt(300000), new GjennomsnittligGrunnbeloep(69108)).beregnet(), .001);
	}

	@Test
	@Spec(": ((6 * G) + ((inntekt - (6 * G)) / 3) - G ) / G = pensjonspoeng")
	public void forInntektMellom6Og12GErPensjonsberegningenFoelgende$spec$() {
		assertEquals("Eksempel fra http://no.wikipedia.org/wiki/Pensjonspoeng"//
				, 7.00, new PensjonspoengKalkulator(new Inntekt(829296), new GjennomsnittligGrunnbeloep(69108)).beregnet(), .001);

		assertEquals("Eksempel med lønn over 6G, men under 12G"//
				, 6.52, new PensjonspoengKalkulator(new Inntekt(729296), new GjennomsnittligGrunnbeloep(69108)).beregnet(), .001);

	}

	@Test
	public void detMaksimaleAntalletPensjonspoengSomKanOpptjenesILoepetAvEtAarEr7() {
	}

	// mangler regler for før etter 1992

}
