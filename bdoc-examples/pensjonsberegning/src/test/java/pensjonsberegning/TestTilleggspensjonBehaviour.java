package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Tilleggspensjon.class)
public class TestTilleggspensjonBehaviour extends ScenarioSupport<TestTilleggspensjonBehaviour> {

	private int grunnbeloep;
	private Double pensjonsprosent;
	private Double sluttpoengtall;
	private Tilleggspensjon tilleggspensjon;

	@Test
	public void tilleggspensjonErLikGrunnbeloep_x_Pensjonsprosent_x_Sluttpoengtall() {
/* 
    Forslag til ny syntax:

  		gitt( grunnbeloep = 10000 );
		og( pensjonsprosent = 0.25 );
		naar( "tillegspensjon beregnes", tilleggspensjon = new Tilleggspensjon(grunnbeloep, pensjonsprosent, sluttpoengtall) );
		saa.skal( tilleggspensjon.beregnet() ).vaere_lik( 125000 ) );
*/		
		gitt.grunnbeloepLik(100000);
		og.pensjonsprosentLik(0.25);
		og.sluttpoengtallLik(5.0);
		naar.beregningAvTilleggspensjonUtfoeres();
		saa.skalTilleggspensjonVaereLik(125000);
	}

	void grunnbeloepLik(int verdi) {
		this.grunnbeloep = verdi;
	}

	void pensjonsprosentLik(final Double verdi) {
		this.pensjonsprosent = verdi;
	}

	void sluttpoengtallLik(final Double verdi) {
		this.sluttpoengtall = verdi;
	}

	void beregningAvTilleggspensjonUtfoeres() {
		this.tilleggspensjon = new Tilleggspensjon(grunnbeloep, pensjonsprosent, sluttpoengtall);
	}

	void skalTilleggspensjonVaereLik(double verdi) {
		assertEquals(verdi, tilleggspensjon.beregnet(), .001);
	}
}
