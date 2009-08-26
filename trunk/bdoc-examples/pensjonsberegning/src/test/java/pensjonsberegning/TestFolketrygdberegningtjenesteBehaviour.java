package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Folketrygdberegningtjeneste.class)
public class TestFolketrygdberegningtjenesteBehaviour {

	private GrunnbeloepTabell grunnbeloepTabell = new GrunnbeloepTabell();
	private Folketrygdberegningtjeneste folketrygdberegningTjeneste = new Folketrygdberegningtjeneste(grunnbeloepTabell);

	private MedlemAvFolketrygden person;
	private Alderspensjon alderspensjon;
	private List<Inntekt> opptjening;

	@Test
	public void skalBeregneAlderspensjonForEtMedlemAvFolketrygden() {
		gittEtMedlemAvFolketrygdenMedSilvitstatusEnslig();
		gitt40AarMedMaksimalOpptjening();
		naarAlderspensjonBlirBeregnetForAaret(2009);
		saaSkalAarligAlderspensjonVaereLik(333823);
		saaSkalTilleggspensjonenUtgjoer(261817);
		saaSkalGrunnpensjonUtgjoer(Grunnbeloep._2009.doubleValue());
		saaSkalSluttpoengtalletBeregnesTil(8.33);
		saaSkalPensjonsprosentenBeregnesTil(0.4365);

		for (Inntekt inntekt : opptjening) {
			_40AarMedMaksimalOpptjening(inntekt.aar(), inntekt.beloep());
		}
	}

	/**
	 * Forellpig BDoc-triks for å kunne dokumentere tabell med inntekt. Her er
	 * det rom for forbedring.
	 */
	void _40AarMedMaksimalOpptjening(int aar, double beloep) {
	}

	void gitt40AarMedMaksimalOpptjening() {
		List<Inntekt> inntekt = new ArrayList<Inntekt>();
		for (int aar = 1970; aar < 2010; aar++) {
			inntekt.add(new Inntekt(aar, (grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(aar) * 12)));
		}
		opptjening = inntekt;
		person.setInntekt(opptjening);
	}

	void gittInntektLik(List<Inntekt> inntekt) {
		person.setInntekt(inntekt);
	}

	void saaSkalGrunnpensjonUtgjoer(double grunnpensjon) {
		assertEquals(grunnpensjon, alderspensjon.getGrunnpensjon(), .001);
	}

	void saaSkalPensjonsprosentenBeregnesTil(double pensjonsprosent) {
		assertEquals(pensjonsprosent, alderspensjon.getTilleggspensjon().getPensjonsprosent(), .0001);
	}

	void saaSkalSluttpoengtalletBeregnesTil(double sluttpoengtall) {
		assertEquals(sluttpoengtall, alderspensjon.getTilleggspensjon().getSluttpoengtall(), .001);
	}

	void saaSkalAarligAlderspensjonVaereLik(int aarligAlderspensjon) {
		assertEquals(aarligAlderspensjon, alderspensjon.beregnet(), .1);
	}

	void gittEtMedlemAvFolketrygdenMedSilvitstatusEnslig() {
		person = new MedlemAvFolketrygden();
	}

	void naarAlderspensjonBlirBeregnetForAaret(int aar) {
		alderspensjon = folketrygdberegningTjeneste.beregnAlderspensjonFor(person, aar);
	}

	void saaSkalTilleggspensjonenUtgjoer(int aarligAlderspensjon) {
		assertEquals(aarligAlderspensjon, alderspensjon.getTilleggspensjon().beregnet(), .1);
	}
}
