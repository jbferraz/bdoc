package pensjonsberegning;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pensjonsberegning.bdoc.Ref;
import pensjonsberegning.bdoc.RefClass;
import pensjonsberegning.bdoc.Story;

@Ref(Story.BEREGNING_AV_ALDERSPENSJON)
@RefClass(Alderspensjon.class)
public class TestAlderspensjonBehaviour {

	private Grunnpensjon grunnpensjon;
	private Tilleggspensjon tilleggspensjon;
	private Alderspensjon alderspensjon;

	@Test
	public void alderspensjonErLikGrunnpensjonPlussTilleggspensjon() {
		gittEnGrunnpensjonLik(70256);
		gittEnTilleggspensjonLik(159056);
		naarAlderspensjonBeregnes();
		saaSkalAlderspensjonPrAarVaereLik(70256 + 159056);
	}

	void gittEnGrunnpensjonLik(double verdi) {
		grunnpensjon = new Grunnpensjon(verdi);
	}

	void gittEnTilleggspensjonLik(double verdi) {
		tilleggspensjon = new Tilleggspensjon(verdi);
	}

	void naarAlderspensjonBeregnes() {
		alderspensjon = new Alderspensjon(grunnpensjon, tilleggspensjon);
	}

	void saaSkalAlderspensjonPrAarVaereLik(double forventetAlderspensjon) {
		assertEquals(forventetAlderspensjon, alderspensjon.verdi(), .001);
	}
}
