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
@RefClass(Folketrygdberegningtjeneste3.class)
public class TestFolketrygdberegningTjenesteBehaviour {

	private GrunnbeloepTabell grunnbeloepTabell = new GrunnbeloepTabell();
	private Folketrygdberegningtjeneste3 folketrygdberegningTjeneste = new Folketrygdberegningtjeneste3(grunnbeloepTabell);

	private MedlemAvFolketrygden person;
	private Alderspensjon alderspensjon;
	
	private List<Inntekt> _40AarMedMaksimalOpptjening() {
		List<Inntekt> inntekt = new ArrayList<Inntekt>();
		for (int aar = 1970; aar < 2010; aar++) {
			inntekt.add(new Inntekt( aar, (grunnbeloepTabell.gjennomsnittligGrunnbeloepFor(aar) * 12) ));
		}
		return inntekt;
	}	

	@Test
	public void skalBeregneAlderspensjonForEtMedlemAvFolketrygden() {
		gittMedlemAvFolketrygden(new MedlemAvFolketrygden() );
		gittInntektLik( _40AarMedMaksimalOpptjening() );
		naarAlderspensjonBlirBeregnetForAaret(2009);
		saaSkalAarligAlderspensjonVaereLik(333823);
		saaUtgjoerTilleggspensjonen(261817);
		saaUtgjoerGrunnpensjon(Grunnbeloep._2009.doubleValue());
		saaBeregnesSluttpoengtalletTil(8.33);
		saaBeregnesPensjonsprosentenTil(0.4365);
	}

	void gittInntektLik(List<Inntekt> inntekt) {
		person.setInntekt( inntekt );		
	}

	void saaUtgjoerGrunnpensjon(double grunnpensjon) {
		assertEquals(grunnpensjon, alderspensjon.getGrunnpensjon(), .001);
	}

	void saaBeregnesPensjonsprosentenTil(double pensjonsprosent) {
		assertEquals(pensjonsprosent, alderspensjon.getTilleggspensjon().getPensjonsprosent(), .0001);

	}

	void saaBeregnesSluttpoengtalletTil(double sluttpoengtall) {
		assertEquals(sluttpoengtall, alderspensjon.getTilleggspensjon().getSluttpoengtall(), .001);
	}

	void saaSkalAarligAlderspensjonVaereLik(int aarligAlderspensjon) {
		assertEquals(aarligAlderspensjon, alderspensjon.beregnet(), .1);
	}

	void gittMedlemAvFolketrygden( MedlemAvFolketrygden medlem ) {
		person = medlem;
	}

	void naarAlderspensjonBlirBeregnetForAaret(int aar) {
		alderspensjon = folketrygdberegningTjeneste.beregnAlderspensjonFor(person, aar);
	}

	void saaUtgjoerTilleggspensjonen(int aarligAlderspensjon) {
		assertEquals(aarligAlderspensjon, alderspensjon.getTilleggspensjon().beregnet(), .1);
	}
}
