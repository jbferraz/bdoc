package pensjonsberegning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestOpptjeningsAarTjeneste {

	private OpptjeningsAarTjeneste opptjeningsAarTjeneste = new OpptjeningsAarTjeneste(new GrunnbeloepTabell());

	@Test
	public void skalOppretteOpptjeningsAarForInntektOverEnG() {
		MedlemAvFolketrygden medlem = new MedlemAvFolketrygden();
		medlem.addInntektForAar(1967, Grunnbeloep._1967 * 2);
		assertFalse(opptjeningsAarTjeneste.opprettOpptjeningsAarFor(medlem).isEmpty());
	}

	@Test
	public void skalIkkeOppretteOpptjeningsAarForInntektUnderEllerLikEnG() {
		MedlemAvFolketrygden medlem = new MedlemAvFolketrygden();
		medlem.addInntektForAar(1967, Grunnbeloep._1967);
		assertTrue(opptjeningsAarTjeneste.opprettOpptjeningsAarFor(medlem).isEmpty());
	}

	@Test
	public void skalAngiPensjonspoengForEtOpptjeningsAar() {
		MedlemAvFolketrygden medlem = new MedlemAvFolketrygden();
		medlem.addInntektForAar(1967, Grunnbeloep._1967 * 3);
		assertEquals(new OpptjeningsAar(1967, 2), opptjeningsAarTjeneste.opprettOpptjeningsAarFor(medlem).get(0));
	}
	
}