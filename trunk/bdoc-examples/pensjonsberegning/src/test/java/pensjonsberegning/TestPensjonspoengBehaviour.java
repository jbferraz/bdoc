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
 * http://www.nav.no/1073750161.cms?kapittel=11
 * 
 * @author Per Otto
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
public class TestPensjonspoengBehaviour {

	@Test
	@Spec("inntekt - G / G = pensjonspoeng")
	@Ignore
	public void forInntektInntil6GErPensjonspoengsberegningenFoelgende$spec$() {
		assertEquals( 4.78, new Pensjonspoeng(400000, new Grunnbeloep( 69108 )).beregnet(), .001 );
		
	}
	
	@Test
	@Spec( "(6 * G) + ((inntekt - (6 * G)) / 3) - G / G = pensjonspoeng")
	public void forInntektMellom6og12GErPensjonsberegningenFoelgende$spec$() {
		
	}
	
	@Test
	public void detMaksimaleAntalletPensjonspoengSomKanOpptjenesILoepetAvEtAarEr7() {
	}
	
	
	//mangler regler for før etter 1992

}
