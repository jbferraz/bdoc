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
@RefClass(FolketrygdberegningTjeneste.class)
public class TestFolketrygdberegningTjenesteBehaviour {

	private GrunnbeloepTabell grunnbeloepTabell = new GrunnbeloepTabell();
	private FolketrygdberegningTjeneste folketrygdberegningTjeneste = new FolketrygdberegningTjeneste(grunnbeloepTabell);

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
		saaHvorAarligTilleggspensjonUtgjoer(261817);
		saaOgGrunnpensjonUtgjoer(Grunnbeloep._2009.doubleValue());
		saaOgSluttpoengtalletBleBeregnetTil(8.33);
		saaOgPensjonsprosentenBleBeregnetTil(0.4365);
	}

	void gittInntektLik(List<Inntekt> inntekt) {
		person.setInntekt( inntekt );		
	}

	void saaOgGrunnpensjonUtgjoer(double grunnpensjon) {
		assertEquals(grunnpensjon, alderspensjon.getGrunnpensjon(), .001);
	}

	void saaOgPensjonsprosentenBleBeregnetTil(double pensjonsprosent) {
		assertEquals(pensjonsprosent, alderspensjon.getTilleggspensjon().getPensjonsprosent(), .0001);

	}

	void saaOgSluttpoengtalletBleBeregnetTil(double sluttpoengtall) {
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

	void saaHvorAarligTilleggspensjonUtgjoer(int aarligAlderspensjon) {
		assertEquals(aarligAlderspensjon, alderspensjon.getTilleggspensjon().beregnet(), .1);
	}
}
