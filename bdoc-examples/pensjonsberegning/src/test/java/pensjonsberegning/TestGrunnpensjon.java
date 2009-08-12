package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.Story;

/**
 * Grunnpensjon: http://www.nav.no/1073750161.cms?kapittel=3
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
public class TestGrunnpensjon {

	private MedlemAvFolketrygden person = new MedlemAvFolketrygden();

	@Before
	public void resetPerson() {
		person = new MedlemAvFolketrygden();
	}

	@Test
	public void somHovedregelTilsvarerFullGrunnpensjonGrunnbeloepetForEnEnsligPensjonsmottaker() {
		Grunnpensjon grunnensjon = new Grunnpensjon(person, 1);
		assertEquals(1, grunnensjon.beregnet(), .001);
	}

	@Test
	public void forPensjonistSomErGiftEllerHarSamboerMedPensjonFraFolketrygdenEllerAfpUtgjoerGrunnpensjonen85ProsentAvGrunnbeloepet() {
		person.setEktefelleEllerSamboerMedPensjonFraFolketrygdenEllerAfp(true);
		assertEquals(0.85d, new Grunnpensjon(person, 1).beregnet(), .001);
	}

	@Test
	public void forPensjonistSomErGiftEllerHarSamboerSomHarInntektSomOverstiger2_x_grunnbeloepetUtgjoerGrunnpensjonen85ProsentAvGrunnbeloepet() {		
		person.setEktefelleEllerSamboerSinInntekt(3);
		assertEquals(0.85d, new Grunnpensjon(person, 1).beregnet(), .001);
		
		person.setEktefelleEllerSamboerSinInntekt(2);
		assertEquals(1, new Grunnpensjon(person, 1).beregnet(), .001);		
	}
}
