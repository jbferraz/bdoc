package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Tilleggspensjon.class)
public class TestTilleggspensjonBehaviour {

	private int grunnbeloep;
	private Double pensjonsprosent;
	private Double sluttpoengtall;
	private Tilleggspensjon tilleggspensjon;

	@Test
	public void tilleggspensjonErLikGrunnbeloep_x_Pensjonsprosent_x_Sluttpoengtall() {
		gittGrunnbeloepLik(100000);
		gittPensjonsprosentLik(0.25);
		gittSluttpoengtallLik(5.0);
		naarBeregningAvTilleggspensjonUtfoeres();
		saaSkalTilleggspensjonVaereLik(125000);
	}

	void gittGrunnbeloepLik(int verdi) {
		this.grunnbeloep = verdi;
	}

	void gittPensjonsprosentLik(final Double verdi) {
		this.pensjonsprosent = verdi;
	}

	void gittSluttpoengtallLik(final Double verdi) {
		this.sluttpoengtall = verdi;
	}

	void naarBeregningAvTilleggspensjonUtfoeres() {
		this.tilleggspensjon = new Tilleggspensjon(grunnbeloep, pensjonsprosent, sluttpoengtall);
	}

	void saaSkalTilleggspensjonVaereLik(double verdi) {
		assertEquals(verdi, tilleggspensjon.beregnet(), .001);
	}
}
